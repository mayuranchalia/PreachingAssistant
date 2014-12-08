package com.temple.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TempleUtility {

	private final static String defaultDateFormat = "dd-MM-yyyy";

	public static Date getSQLDateFromString(String textdate, String formatText)
			throws ParseException {
		if (formatText == null || formatText.isEmpty()) {
			formatText = defaultDateFormat;
		}
		DateFormat formatter = new SimpleDateFormat(formatText);
		java.util.Date myDate = formatter.parse(textdate);
		Date sqlDate = new java.sql.Date(myDate.getTime());

		return sqlDate;
	}

	public static void main(String[] args) {
		try {
			System.out.println(getSQLDateFromString("11-09-2014"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Date getSQLDateFromString(String textdate)
			throws ParseException {

		DateFormat formatter = new SimpleDateFormat(defaultDateFormat);
		java.util.Date myDate = formatter.parse(textdate);
		Date sqlDate = new java.sql.Date(myDate.getTime());

		return sqlDate;
	}

}
