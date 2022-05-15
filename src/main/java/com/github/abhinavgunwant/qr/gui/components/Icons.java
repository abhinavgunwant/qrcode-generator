package com.github.abhinavgunwant.qr.gui.components;

public enum Icons {
	REFRESH("\uf2f9"),
//	DOWNLAOD("\uf019"),
	SAVE("\uf0c7");
	
	private final String code;
	
	private Icons(String code) {
		this.code = code;
	}
	
	public String toString() {
		return code;
	}
}
