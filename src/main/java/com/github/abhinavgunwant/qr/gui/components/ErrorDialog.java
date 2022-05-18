package com.github.abhinavgunwant.qr.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import com.github.abhinavgunwant.qr.Consts;

/**
 * A simple error dialog.
 * 
 * @author abhinavgunwant
 */
public class ErrorDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private Panel dialogPanel;
	private Panel textPanel;
	private Panel buttonPanel;
	private JButton closeButton;
	private JLabel textLabel;

	public ErrorDialog() {
		dialogPanel = new Panel();
		textPanel = new Panel();
		buttonPanel = new Panel();
		closeButton = new JButton();
		textLabel = new JLabel();
	}
	
	public ErrorDialog(Frame owner, String title, String text) {
		super(owner, title, true);
		
		dialogPanel = new Panel();
		textPanel = new Panel();
		buttonPanel = new Panel();
		closeButton = new JButton("Close");
		textLabel = new JLabel(text);
		
		System.out.println("Error dialog!");
		
		setupUI();
	}

	private void setupUI() {
		setSize(new Dimension(300, 150));
		setBackground(Consts.FRAME_BACKGROUND);
		
		dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
		textPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		closeButton.setBackground(Consts.BUTTON_BACKGROUND);
		closeButton.setForeground(Color.WHITE);
		closeButton.setBorder(new LineBorder(Consts.BUTTON_BORDER));
		closeButton.setMinimumSize(new Dimension(100, 25));
		closeButton.addActionListener(event -> setVisible(false));
		
		textLabel.setForeground(Color.WHITE);
		
		textPanel.add(textLabel);
		buttonPanel.add(closeButton);
		
		dialogPanel.add(textPanel);
		dialogPanel.add(buttonPanel);
		
		add(dialogPanel);
	}
}
