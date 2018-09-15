package br.uff.mh.mestrado.excel;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.utils.ExcelUtils;
import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Subset;

public class SheetCollection {
	private static Logger logger = Logger.getLogger(SheetCollection.class);

	public void create(Excel excel, Config c, List<Heuristic> list) {
		logger.debug("Creating Sheet Collection");

		for (Heuristic heuristic : list) {
			Sheet sh = excel.createSheet(heuristic.getName());

			createHeader(sh, heuristic);
			createPacking(sh, heuristic);
			createCollection(sh, heuristic);
		}

	}

	private void createHeader(Sheet sh, Heuristic h) {
		Row row = sh.createRow(0);
		ExcelUtils.createCell(row, 0, h.taskName());
	}

	private void createPacking(Sheet sh, Heuristic h) {
		CellStyle cs = sh.getWorkbook().createCellStyle();
		Font font = sh.getWorkbook().createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		cs.setFont(font);
		cs.setAlignment(CellStyle.ALIGN_CENTER);

		Row row = sh.createRow(1);
		int col = 0;
		ExcelUtils.createCell(row, cs, col++, "P");
		ExcelUtils.createCell(row, cs, col++, "Ci");

		Packing p = h.getCollection().getPacking();

		for (int i = 1; i < p.size(); i++) {
			row = sh.createRow(i + 1);
			col = 0;
			int value = (p.get(i) ? 1 : 0);
			ExcelUtils.createCell(row, cs, col++, value);
			ExcelUtils.createCell(row, cs, col++, h.getCollection().get(i).getCost());
			ExcelUtils.createCell(row, cs, col++, "->");
		}

	}

	private void createCollection(Sheet sh, Heuristic heuristic) {
		List<Subset> list = heuristic.getCollection().getCollection();
		for (int i = 1; i < list.size(); i++) {
			int col = 3;
			Subset subset = list.get(i);
			Row row = sh.getRow(i + 1);

			for (int j = 0; j < subset.size(); j++) {
				int value = (subset.get(j) ? 1 : 0);
				ExcelUtils.createCell(row, col++, value);
			}
		}
	}

}
