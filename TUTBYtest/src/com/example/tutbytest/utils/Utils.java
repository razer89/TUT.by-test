package com.example.tutbytest.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
	
	public static String convertDate(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.ENGLISH);
        Date d = new Date(date);
		return sdf.format(d);
	}
	
	public static String convertSeconds(long seconds) {
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.ENGLISH);
        Date d = new Date(seconds);
		return sdf.format(d);
	}
}