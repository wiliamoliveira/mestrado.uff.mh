package br.uff.mh.mestrado.utils;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

	public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
	// public static final DecimalFormat df = new DecimalFormat("#.##");
	public static final DecimalFormat df = new DecimalFormat("#");

	public static final String getStringNow() {
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String format(Date d) {
		if (d == null) {
			return "null";
		} else {
			return sdf.format(d);
		}
	}

	public static final String format(java.util.Date d) {
		if (d == null) {
			return "null";
		} else {
			return sdf.format(d);
		}
	}
	
	public static long getElapsedTimeInSecs(long start) {
		return  (long) ((new Long(System.nanoTime()) - new Double(start)) / ((double) 1000000000));
	}

	public static double getElapsedTime(long start) {
		return ((new Long(System.nanoTime()) - new Double(start)) / ((double) 1000000));
		// return (System.nanoTime() - start) / 1000000;
	}

	public static String getElapsedTimeString(long start) {
		return formatElapsedTimeString(getElapsedTime(start));
	}

	public static String formatElapsedTimeString(double time) {
		if (time > 10000)
			return df.format(time / 1000) + " sec";
		else
			return df.format(time) + " ms";
	}

	public static double getFormatNanoTime(long start) {
		return (new Double(start) / ((double) 1000000));
	}

	public static int compareNow(int hourOfDay, int minute) {
		Calendar now = Calendar.getInstance();
		Calendar tmp = Calendar.getInstance();

		tmp.set(Calendar.HOUR_OF_DAY, hourOfDay);
		tmp.set(Calendar.MINUTE, minute);

		return now.compareTo(tmp);
	}

}
