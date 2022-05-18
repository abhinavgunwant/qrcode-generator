/**
 * 
 */
package com.github.abhinavgunwant.qr.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Action;
import javax.swing.JButton;

import com.github.abhinavgunwant.qr.Consts;

/**
 * Implements the custom button UI.
 * 
 * @author abhinavgunwant
 */
public class IconButton extends JButton {
	private static final long serialVersionUID = 1L;
	Icons icon = null;

	public IconButton() {
		setupUI();
	}
	
	public IconButton(Icons icon) {
		this.icon = icon;
		
		setupUI();
	}

	/**
	 * @param text
	 */
	public IconButton(String text) {
		super(text);
		setupUI();
	}

	/**
	 * @param a
	 */
	public IconButton(Action a) {
		super(a);
		setupUI();
	}
	
	public void setIcon(Icons icon) {
		this.icon = icon;
		setText(icon.toString());
	}
	
	private void setupUI() {
        setBackground(Consts.BUTTON_BACKGROUND);
        setForeground(Color.WHITE);
        
        try (InputStream is = getClass().getResourceAsStream("/Font Awesome 6 Free-Solid-900.otf")) {
    		Font fontawesome = Font.createFont(Font.TRUETYPE_FONT, is);
    		fontawesome = fontawesome.deriveFont(Font.BOLD, 12f);
    		
    		setFont(fontawesome);
    		if (icon != null) {
    			setText(icon.toString());
    		}
    	} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}
}
