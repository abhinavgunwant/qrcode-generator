package com.github.abhinavgunwant.qr.gui.components;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.github.abhinavgunwant.qr.Consts;

public class Panel extends JPanel {
	private static final long serialVersionUID = 1L;

	public Panel() {
		setupUI();
	}

	public Panel(LayoutManager layout) {
		super(layout);
		setupUI();
	}

	public Panel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		setupUI();
	}

	public Panel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		setupUI();
	}
	
	private void setupUI() {
		setBackground(Consts.FRAME_BACKGROUND);
	}
}
