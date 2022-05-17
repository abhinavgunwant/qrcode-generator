package com.github.abhinavgunwant.qr.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.github.abhinavgunwant.qr.Consts;
import com.github.abhinavgunwant.qr.Generator;
import com.github.abhinavgunwant.qr.gui.components.ErrorDialog;
import com.github.abhinavgunwant.qr.gui.components.IconButton;
import com.github.abhinavgunwant.qr.gui.components.Icons;
import com.github.abhinavgunwant.qr.gui.components.Panel;
import com.google.zxing.WriterException;

public class MainWindow {
    private static int qrcodeHeight = 200;
    private static int qrcodeWidth = 200;
    private static File currentQRCodeFile;

    boolean dark;

    JFrame mainFrame;
    JPanel leftPanel;
    JPanel rightPanel;
    JPanel errCorPanel;
    JPanel inputTxtAreaPanel;
    JPanel generateQrPanel;
    Panel qrActionPanel;
    Panel qrPreviewLabelPanel;
    Panel qrPreviewPanel;
    Panel qrPreviewImagePanel;
    JLabel qrCodeLabel;
    JLabel previewLabel;
    JLabel textAreaLabel;
    JLabel errCorLabel;
    JTextArea inputTextArea;
    IconButton refreshQRButton;
    IconButton saveQRButton;
    JComboBox<String> errCorCombo;
    
    ActionListener generateQRAction;

    public MainWindow() {
        dark = true;

        mainFrame = new JFrame();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        errCorPanel = new JPanel();
        inputTxtAreaPanel = new JPanel();
        generateQrPanel = new JPanel();
        qrActionPanel = new Panel();
        qrPreviewPanel = new Panel();
        qrPreviewLabelPanel = new Panel();
        qrPreviewImagePanel = new Panel();
        previewLabel = new JLabel();
        textAreaLabel = new JLabel();
        qrCodeLabel = new JLabel();
        errCorLabel = new JLabel();
        inputTextArea = new JTextArea();
        refreshQRButton = new IconButton(Icons.REFRESH);
        saveQRButton = new IconButton(Icons.SAVE);
        errCorCombo = new JComboBox<String>(new String[] {"L", "M", "H", "Q"});
        
        generateQRAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	generateQRCode();
            }
        };

        setupUI();
    }

    private void setInitialQR() {
    	BufferedImage qrCodeBufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        String initialString = "Start typing in input field";
        Graphics g = qrCodeBufferedImage.getGraphics();
        FontMetrics fm = g.getFontMetrics();
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 200, 200);
        g.setColor(Color.BLACK);
        g.drawString(initialString, (qrcodeWidth - fm.stringWidth(initialString))/2, qrcodeHeight/2);
        g.dispose();

        qrCodeLabel.setIcon(new ImageIcon(qrCodeBufferedImage));
    }
    
    private void setCustomFonts() {
    	try (InputStream is = getClass().getResourceAsStream("/Font Awesome 6 Free-Solid-900.otf")) {
    		Font fontawesome = Font.createFont(Font.TRUETYPE_FONT, is);
    		fontawesome = fontawesome.deriveFont(Font.BOLD, 12f);
    		
    		refreshQRButton.setFont(fontawesome);
    		refreshQRButton.setText("\uf2f9");
    	} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
    }

    private void setupUI() {
        mainFrame.setSize(480, 480);
        mainFrame.setMinimumSize(new Dimension(480, 480));
        mainFrame.setLayout(new BoxLayout(mainFrame, BoxLayout.X_AXIS));
        mainFrame.setTitle("QR Code Generator");
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new GridLayout());
        mainFrame.setBackground(Consts.FRAME_BACKGROUND);

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setBackground(Consts.FRAME_BACKGROUND);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Consts.FRAME_BACKGROUND);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        
        errCorPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        errCorPanel.setBackground(Consts.FRAME_BACKGROUND);
        inputTxtAreaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        inputTxtAreaPanel.setBackground(Consts.FRAME_BACKGROUND);
        inputTxtAreaPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        generateQrPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        generateQrPanel.setBackground(Consts.FRAME_BACKGROUND);
        qrActionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        qrActionPanel.setMaximumSize(new Dimension(300, 40));
        qrPreviewPanel.setLayout(new BoxLayout(qrPreviewPanel, BoxLayout.Y_AXIS));
        qrPreviewPanel.setMaximumSize(new Dimension(300, 235));
        qrPreviewLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        qrPreviewLabelPanel.setMaximumSize(new Dimension(300, 25));
        qrPreviewImagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        qrPreviewImagePanel.setMaximumSize(new Dimension(300, 300));

        textAreaLabel.setText("Input Text:");
        textAreaLabel.setForeground(Color.WHITE);
        previewLabel.setText("Preview:");
        previewLabel.setForeground(Color.WHITE);
        errCorLabel.setText("Error Correction Level:");
        errCorLabel.setForeground(Color.WHITE);

        refreshQRButton.addActionListener(generateQRAction);
        saveQRButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		if (currentQRCodeFile == null) {
        			ErrorDialog errDiag = new ErrorDialog(
        					mainFrame,
        					"Error",
        					"<html><center>No QR code"
        					+ " generated yet.<br />Please start typing"
        					+ " something in input<br />field before"
        					+ " attempting to save.</center></html>");
        			errDiag.setVisible(true);
//        			JDialog dialog = new JDialog(mainFrame, "Error", true);
//        			Panel dialogPanel = new Panel();
//        			Panel textPanel = new Panel();
//        			Panel buttonPanel = new Panel();
//        			
//        			JButton closeButton = new JButton("Close");
//        			JLabel text = new JLabel("<html><center>No QR code"
//        					+ " generated yet.<br />Please start typing"
//        					+ " something in input<br />field before"
//        					+ " attempting to save.</center></html>");
//
//        			dialog.setSize(new Dimension(300, 150));
//        			dialog.setBackground(Consts.FRAME_BACKGROUND);
//        			
//        			dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
//        			textPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//        			buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//        			
//        			closeButton.setBackground(Consts.BUTTON_BACKGROUND);
//        			closeButton.setForeground(Color.WHITE);
//        			closeButton.setBorder(new LineBorder(Consts.BUTTON_BORDER));
//        			closeButton.setMinimumSize(new Dimension(100, 25));
//        			closeButton.addActionListener(new ActionListener() {
//        				@Override
//        				public void actionPerformed(ActionEvent e) {
//        					dialog.setVisible(false);
//        				}
//        			});
//        			
//        			text.setForeground(Color.WHITE);
//        			
//        			textPanel.add(text);
//        			buttonPanel.add(closeButton);
//        			
//        			dialogPanel.add(textPanel);
//        			dialogPanel.add(buttonPanel);
//        			
//        			dialog.add(dialogPanel);
//        			dialog.setVisible(true);
//        			
        			return;
        		}
        		
        		JFileChooser fc = new JFileChooser();
        		fc.setDialogTitle("Save QR code's image file");
        		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
    			fc.setFileFilter(new FileNameExtensionFilter("PNG Image", "png"));
        		
        		int option = fc.showSaveDialog(mainFrame);
        		
        		if (option == JFileChooser.APPROVE_OPTION) {
        			File targetFile = fc.getSelectedFile();
        			
        			try (
    					FileInputStream fis = new FileInputStream(currentQRCodeFile);
    					FileOutputStream fos = new FileOutputStream(targetFile)
					) {
        				byte bytes[] = new byte[1024];
        				int byteIndex;
        				
        				while ((byteIndex = fis.read(bytes)) != -1) {
        					fos.write(bytes, 0, byteIndex);
        				}
        			} catch (IOException e1) {
						e1.printStackTrace();
					}
        		}
        	}
        });
        
        errCorCombo.addActionListener(generateQRAction);

        inputTextArea.setBackground(Consts.BUTTON_BACKGROUND);
        inputTextArea.setForeground(Color.WHITE);
        inputTextArea.setBorder(BorderFactory.createLineBorder(Consts.BUTTON_BORDER));
        inputTextArea.setLineWrap(true);
        inputTextArea.setRows(10);
        inputTextArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        inputTextArea.getDocument().addDocumentListener(new DocumentListener() {
        	@Override
        	public void insertUpdate(DocumentEvent e) { generateQRCode(); }

        	@Override
        	public void removeUpdate(DocumentEvent e) { generateQRCode(); }
        	
        	@Override
        	public void changedUpdate(DocumentEvent e) { generateQRCode(); }
        });
        
        inputTxtAreaPanel.add(textAreaLabel);
        qrPreviewLabelPanel.add(previewLabel);
        qrPreviewImagePanel.add(qrCodeLabel);
        
        addAll(errCorPanel, errCorLabel, errCorCombo);

        addAll(qrPreviewPanel, qrPreviewLabelPanel, qrPreviewImagePanel);
        addAll(qrActionPanel, refreshQRButton, saveQRButton);
        addAll(leftPanel, inputTxtAreaPanel, inputTextArea, errCorPanel, generateQrPanel);
        addAll(rightPanel, qrPreviewPanel, qrActionPanel);
        
        mainFrame.add(leftPanel);
        mainFrame.add(rightPanel);

        setInitialQR();
    }
    
    private void generateQRCode() {
    	if (inputTextArea.getText().isEmpty()) {
    		setInitialQR();
    		return;
    	}
    	
        try {
        	currentQRCodeFile = Generator.generateCode(
					inputTextArea.getText(),
					qrcodeHeight,
					(String) errCorCombo.getSelectedItem()
			);
        	
        	qrCodeLabel.setIcon(new ImageIcon(ImageIO.read(
        			currentQRCodeFile
        	)));
		} catch (WriterException | IOException e1) {
			e1.printStackTrace();
		}
    }
    
    /**
     * Adds multiple components to a single parent {@code JComponent}
     * 
     * @param parentComponent parent component
     * @param components child components
     */
    private void addAll(JComponent parentComponent, Component... components) {
    	for (Component c : components) {
    		parentComponent.add(c);
    	}
    }

    public void show() {
        mainFrame.setVisible(true);
    }
}
