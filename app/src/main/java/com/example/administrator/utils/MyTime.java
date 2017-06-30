package com.example.administrator.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTime {

	// mm-DD HH:mm:SS

	public static String getTime() {
		// Ctrl+Shift+M
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat f = new SimpleDateFormat("mm-DD HH:mm:ss");
		return f.format(date);
	}
	public static String getTime(long time) {
		// Ctrl+Shift+M
		Date date = new Date(time);
		SimpleDateFormat f = new SimpleDateFormat("mm-DD HH:mm:ss");
		return f.format(date);
	}
}
