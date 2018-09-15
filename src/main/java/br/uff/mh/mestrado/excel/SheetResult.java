package br.uff.mh.mestrado.excel;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import br.uff.mh.mestrado.utils.ExcelUtils;
import br.uff.mh.mestrado.vo.ResultsVo;

public class SheetResult {
	private static Logger logger = Logger.getLogger(SheetResult.class);

	public void create(Excel excel, List<ResultsVo> results) {
		logger.debug("Creating Sheet Result");

		Sheet sh = excel.createSheet("Results");
		createHeader(sh);

		int line = 1;
		for (ResultsVo r : results) {
			createCell(sh, line, r);
			line++;
		}

		logger.debug("Creating Sheet Greedy");
	}

	private void createHeader(Sheet sh) {
		CellStyle cs = sh.getWorkbook().createCellStyle();
		Font font = sh.getWorkbook().createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		cs.setFont(font);
		cs.setAlignment(CellStyle.ALIGN_CENTER);

		Row row = sh.createRow(0);
		row.setRowStyle(cs);

		int col = 0;
		ExcelUtils.createCell(row, col++, "taskName", cs);
		ExcelUtils.createCell(row, col++, "Min Cost", cs);
		ExcelUtils.createCell(row, col++, "Max Cost", cs);
		ExcelUtils.createCell(row, col++, "LS Cost", cs);
		ExcelUtils.createCell(row, col++, "PR Cost", cs);
		ExcelUtils.createCell(row, col++, "GRASP Min Cost", cs);
		ExcelUtils.createCell(row, col++, "GRASP Max Cost", cs);
		ExcelUtils.createCell(row, col++, "Max Time", cs);
		ExcelUtils.createCell(row, col++, "Total Time", cs);
	}

	private void createCell(Sheet sh, int line, ResultsVo r) {
		Row row = sh.createRow(line);
		int col = 0;
		ExcelUtils.createCell(row, col++, r.getTaskName());
		ExcelUtils.createCell(row, col++, r.getMinCost());
		ExcelUtils.createCell(row, col++, r.getMaxCost());
		ExcelUtils.createCell(row, col++, r.getLsCost());
		ExcelUtils.createCell(row, col++, r.getPrCost());
		ExcelUtils.createCell(row, col++, r.getGraspMinCost());
		ExcelUtils.createCell(row, col++, r.getGraspMaxCost());
		ExcelUtils.createCell(row, col++, r.getMaxTime());
		ExcelUtils.createCell(row, col++, r.getTotalTime());
	}

}
