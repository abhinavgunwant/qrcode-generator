package com.github.abhinavgunwant.qr.gui.components;

import java.awt.Color;
//import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
//import java.awt.GraphicsConfiguration;
//import java.awt.Window;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

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

//	public ErrorDialog(Frame owner) {
//		super(owner);
//	}
//
//	public ErrorDialog(Dialog owner) {
//		super(owner);
//	}
//
//	public ErrorDialog(Window owner) {
//		super(owner);
//	}
//
//	public ErrorDialog(Frame owner, boolean modal) {
//		super(owner, modal);
//	}
//
//	public ErrorDialog(Frame owner, String title) {
//		super(owner, title);
//	}
//
//	public ErrorDialog(Dialog owner, boolean modal) {
//		super(owner, modal);
//	}
//
//	public ErrorDialog(Dialog owner, String title) {
//		super(owner, title);
//	}
//
//	public ErrorDialog(Window owner, ModalityType modalityType) {
//		super(owner, modalityType);
//	}
//
//	public ErrorDialog(Window owner, String title) {
//		super(owner, title);
//	}
//
//	public ErrorDialog(Frame owner, String title, boolean modal) {
//		super(owner, title, modal);
//	}
//
//	public ErrorDialog(Dialog owner, String title, boolean modal) {
//		super(owner, title, modal);
//	}
//
//	public ErrorDialog(Window owner, String title, ModalityType modalityType) {
//		super(owner, title, modalityType);
//	}
//
//	public ErrorDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
//		super(owner, title, modal, gc);
//	}
//
//	public ErrorDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
//		super(owner, title, modal, gc);
//	}
//
//	public ErrorDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
//		super(owner, title, modalityType, gc);
//	}

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
