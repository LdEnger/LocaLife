package com.hiveview.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilThread {
	private static ThreadLocal<DateFormat> fullDateFormatter = new ThreadLocal<DateFormat>();
	private static ThreadLocal<DateFormat> longDateFormatter = new ThreadLocal<DateFormat>();

	public static String date2Full(final Date date) {
		DateFormat fd = fullDateFormatter.get();
		if (fd == null) {
			fd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			fullDateFormatter.set(fd);
		}
		return fd.format(date);
	}

	public static String date2Long(final Date date) {
		DateFormat ld = longDateFormatter.get();
		if (ld == null) {
			ld = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			longDateFormatter.set(ld);
		}
		return ld.format(date);
	}
}
