package com.github.abhinavgunwant.qr.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class MainWindow {
    private static final Color BUTTON_BACKGROUND = new Color(0x888888);
    private static final Color FRAME_BACKGROUND = new Color(0x444444);
    private static int qrcodeHeight = 200;
    private static int qrcodeWidth = 200;

    boolean dark;

    JFrame mainFrame;
    JPanel leftPanel;
    JPanel rightPanel;
    JPanel errCorPanel;
    BufferedImage qrCodeBufferedImage;
    ImageIcon qrcodeImageIcon;
    JLabel qrCodeLabel;
    JLabel previewLabel;
    JLabel textAreaLabel;
    JLabel errCorLabel;
    JTextArea inputTextArea;
    JButton createQRButton;
    JComboBox<String> errCorCombo;

    public MainWindow() {
        dark = true;

        mainFrame = new JFrame();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        errCorPanel = new JPanel();
        previewLabel = new JLabel();
        textAreaLabel = new JLabel();
        qrCodeLabel = new JLabel();
        errCorLabel = new JLabel();
        inputTextArea = new JTextArea();
        createQRButton = new JButton();
        qrCodeBufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        qrcodeImageIcon = new ImageIcon(qrCodeBufferedImage);
        errCorCombo = new JComboBox<String>(new String[] {"L", "M", "H", "Q"});

        setupUI();
    }

    private void setInitialQR() {
        String initialString = "Click on \"Generate QR\" button";
        Graphics g = qrCodeBufferedImage.getGraphics();
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 200, 200);
        g.setColor(Color.BLACK);
        g.drawString(initialString, (qrcodeWidth - fm.stringWidth(initialString))/2, qrcodeHeight/2);
        g.dispose();
    }

    private void setupUI() {
        mainFrame.setSize(640, 480);
        mainFrame.setLayout(null);
        mainFrame.setTitle("QR Code Generator");
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new GridLayout());
        mainFrame.setBackground(FRAME_BACKGROUND);

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setBackground(FRAME_BACKGROUND);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(FRAME_BACKGROUND);
        rightPanel.setSize(qrcodeHeight, qrcodeWidth);
        errCorPanel.setLayout(new BoxLayout(errCorPanel, BoxLayout.X_AXIS));
        errCorPanel.setBackground(FRAME_BACKGROUND);

        textAreaLabel.setText("Input Text");
        textAreaLabel.setForeground(Color.WHITE);
        previewLabel.setText("Preview:");
        previewLabel.setForeground(Color.WHITE);
        errCorLabel.setText("Error Correction Level:");
        errCorLabel.setForeground(Color.WHITE);
        qrCodeLabel.setIcon(qrcodeImageIcon);

        createQRButton.setText("Generate QR");
        createQRButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Clicked!");
            }
        });
        createQRButton.setBackground(BUTTON_BACKGROUND);
        createQRButton.setForeground(Color.WHITE);

        inputTextArea.setBackground(FRAME_BACKGROUND);
        inputTextArea.setForeground(Color.WHITE);
        inputTextArea.setBorder(BorderFactory.createLineBorder(BUTTON_BACKGROUND));

        errCorPanel.add(errCorLabel);
        errCorPanel.add(errCorCombo);

        leftPanel.add(textAreaLabel);
        leftPanel.add(inputTextArea);
        leftPanel.add(errCorPanel);
        leftPanel.add(createQRButton);
        rightPanel.add(previewLabel);
        rightPanel.add(qrCodeLabel);
        mainFrame.add(leftPanel);
        mainFrame.add(rightPanel);

        setInitialQR();
    }

    public void show() {
        mainFrame.setVisible(true);
    }
}
