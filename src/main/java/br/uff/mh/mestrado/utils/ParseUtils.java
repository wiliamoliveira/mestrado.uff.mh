package br.uff.mh.mestrado.utils;

import java.text.DecimalFormat;

import org.apache.commons.lang3.math.NumberUtils;

public class ParseUtils {
	// private DecimalFormat formatter = new DecimalFormat("0");
	public static final DecimalFormat df = new DecimalFormat("#.##");

	public static String toString(double d) {
		return df.format(d);
//		return String.valueOf(d);
	}

	public static String toString(int i) {
		return Integer.toString(i);
	}

	public static double toDouble(String s) {
		return NumberUtils.toDouble(s);
	}

	public static int toInteger(String s) {
		return NumberUtils.toInt(s);
	}
}
