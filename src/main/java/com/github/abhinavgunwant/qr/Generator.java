package com.github.abhinavgunwant.qr;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicBorders.MarginBorder;

import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import javafx.print.Printer.MarginType;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


/**
 * Singleton class that generates QR codes.
 */
public class Generator {
    private static final String MINI_LOGO_NAME = "micro-logo.png";

    private static Generator instance = null;

    private Generator () {}

    public static Generator getInstance() {
        if (instance == null) {
            instance = new Generator();
        }

        return instance;
    }

    private int dotWidth(BufferedImage bi) {
        for(int i=0; i<bi.getWidth(); ++i) {
            if ((bi.getRGB(i, i) & 0x00ffffff) == 0) {

                for (int j=i+1; j<bi.getWidth(); ++j) {
                    if ((bi.getRGB(j, i) & 0x00ffffff) > 0) {

                        return (j-i+1)/7;
                    }
                }
            }
        }

        return -1;
    }

    private Image resizedQRCode(BufferedImage bi) {
        boolean tl = false; // scan from top-left done or not
        boolean br = false; // scan from bottom-right done or not

        int topLeft = -1;
        int bottomRight = -1;

        for(
            int i=0, j=0, i2=bi.getWidth()-1, j2=bi.getHeight()-1;
            i<bi.getWidth() && j<bi.getHeight() && i2>0 && j2>0;
            ++i, ++j, --i2, --j2
        ) {
            if (!tl && (bi.getRGB(i, j) & 0x00ffffff) != 0xffffff) {
                tl = true;
                topLeft = i;
            }

            if (!br && (bi.getRGB(i2, j2) & 0x00ffffff) != 0xffffff) {
                br = true;
                bottomRight = i2;
            }

            if (tl && br) {
                break;
            }
        }

        System.out.println("Margin: " + topLeft + ", " + bottomRight);

        return bi.getScaledInstance(1, 1, 0);
    }

    public File generateCode(String url, File outputFile, int dimension)
        throws WriterException, IOException {
        
        Map<EncodeHintType, Object> hints
            = new HashMap<EncodeHintType, Object>();

        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(
            url, BarcodeFormat.QR_CODE, dimension, dimension, hints
        );

        String filePathStr = outputFile.getPath();
        Path filePath = FileSystems.getDefault().getPath(filePathStr);
        MatrixToImageWriter.writeToPath(matrix, "png", filePath);
        

        return outputFile;
    }

    public File generateCode(String url, String filename, int dimension)
        throws WriterException, IOException {
        // BitMatrix matrix = new MultiFormatWriter().encode(
        //     new String(url.getBytes("ASCII"), "ASCII"),
        //     BarcodeFormat.QR_CODE, dimension, dimension);

        File file = new File(".\\" + filename);
        // String filePathStr = file.getPath();
        // Path filePath = FileSystems.getDefault().getPath(filePathStr);
        // MatrixToImageWriter.writeToPath(matrix, "png", filePath);

        return generateCode(url, file, dimension);
    }

    public File generateCodeWithLogo(
            String url, String filename, int dimension
        ) throws WriterException, IOException {
        
        File qrcodeFile = File.createTempFile("dfgh", ".png");

        generateCode(url, qrcodeFile, dimension);

        BufferedImage img = ImageIO.read(qrcodeFile);

        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        int miniLogoWidth =  imgWidth/6;
        int miniLogoHeight = imgHeight/6;
        int miniLogoStartX = (imgWidth/2) - (miniLogoWidth/2);
        int miniLogoStartY = (imgHeight/2) - (miniLogoHeight/2);
        int miniLogoBorderWidth = miniLogoWidth/8;

        Image miniLogo = ImageIO.read(
            getClass().getClassLoader().getResource(MINI_LOGO_NAME)
        ).getScaledInstance(miniLogoWidth, miniLogoHeight, Image.SCALE_SMOOTH);

        BufferedImage output = new BufferedImage(
            img.getWidth(),
            img.getHeight(),
            BufferedImage.TYPE_INT_RGB
        );

        Graphics outGraphics = output.getGraphics();

        outGraphics.setColor(Color.WHITE);
        outGraphics.drawImage(img, 0, 0, null);
        
        outGraphics.fillRect(
            miniLogoStartX - miniLogoBorderWidth - 1,
            miniLogoStartY - miniLogoBorderWidth - 1,
            miniLogoWidth + 2*miniLogoBorderWidth + 2,
            miniLogoHeight + 2*miniLogoBorderWidth + 2
        );
        outGraphics.drawImage(miniLogo, miniLogoStartX, miniLogoStartY, null);

        outGraphics.dispose();

        File outputFile = new File("./" + filename);

        ImageIO.write(output, "png", outputFile);

        
        System.out.println("50, 50: " + dotWidth(output));

        return qrcodeFile;
    }
}
