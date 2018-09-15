package br.uff.mh.mestrado.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

public class ExcelUtils {

	public static void createCell(Row row, CellStyle cs, int col, String value) {
		Cell cell = row.createCell(col);
		cell.setCellValue(value);
		cell.setCellStyle(cs);
	}

	public static void createCell(Row row, int col, String value) {
		Cell cell = row.createCell(col);
		cell.setCellValue(value);
	}

	public static void createCell(Row row, CellStyle cs, int col, boolean value) {
		Cell cell = row.createCell(col);
		cell.setCellValue(value);
		cell.setCellStyle(cs);
	}

	public static void createCell(Row row, int col, boolean value) {
		Cell cell = row.createCell(col);
		cell.setCellValue(value);
	}

	public static void createCell(Row row, int col, double value) {
		Cell cell = row.createCell(col);
		cell.setCellValue(value);
	}

	public static void createCell(Row row, CellStyle cs, int col, double value) {
		Cell cell = row.createCell(col);
		cell.setCellValue(value);
		cell.setCellStyle(cs);
	}
	
	public static void createCell(Row row, CellStyle cs, int col, int value) {
		Cell cell = row.createCell(col);
		cell.setCellValue(value);
		cell.setCellStyle(cs);
	}

	public static void createCell(Row row, int col, int value) {
		Cell cell = row.createCell(col);
		cell.setCellValue(value);
	}

	public static void createCell(Row row, int col, String value, CellStyle cs) {
		Cell cell = row.createCell(col);
		cell.setCellStyle(cs);
		cell.setCellValue(value);
	}

	public static void createCell(Row row, int col, int value, CellStyle cs) {
		Cell cell = row.createCell(col);
		cell.setCellStyle(cs);
		cell.setCellValue(value);
	}
}
