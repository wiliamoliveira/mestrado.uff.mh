package br.uff.mh.mestrado.excel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.utils.ExcelUtils;

public class SheetInput {
	private static Logger logger = Logger.getLogger(SheetInput.class);

	public void create(Excel excel, Config c) {
		logger.debug("Creating Sheet Input");
		Sheet sh = excel.createSheet("Input");

		CellStyle cs = createCellStype(sh);

		int line = 0;
		createLine(sh, cs, line++, "maxCollectionSize", c.getMaxCollectionSize());
		createLine(sh, cs, line++, "maxSubsetSize", c.getMaxSubsetSize());
		createLine(sh, cs, line++, "maxCost", c.getMaxCost());
		createLine(sh, cs, line++, "distribution", c.getDistribution());
		createLine(sh, cs, line++, "alfa", c.getAlfa());
		createLine(sh, cs, line++, "seed", c.getSeed());
		createLine(sh, cs, line++, "greedySeed", c.getGreedySeed());
		createLine(sh, cs, line++, "greedyRandomLoops", c.getGreedyRandomLoops());
		createLine(sh, cs, line++, "localSearchRandomLoops", c.getLocalSearchRandomLoops());
		createLine(sh, cs, line++, "graspRandomLoops", c.getGraspRandomLoops());
		createLine(sh, cs, line++, "pathRelinkingCandidateListMaxSize", c.getPathRelinkingCandidateListMaxSize());
		createLine(sh, cs, line++, "Local Search", c.getLs().getItem().toString());
		createLine(sh, cs, line++, "isStrategyHCMC", c.isStrategyHCMC());
		createLine(sh, cs, line++, "isStrategyHCMCR", c.isStrategyHCMCR());
		createLine(sh, cs, line++, "isStrategyHCMCRalfa", c.isStrategyHCMCRAlfa());
		createLine(sh, cs, line++, "isStrategyHCTNV", c.isStrategyHCTNV());
		createLine(sh, cs, line++, "isStrategyLS", c.isStrategyLS());
		createLine(sh, cs, line++, "isStrategyPRFor", c.isStrategyPRFor());
		createLine(sh, cs, line++, "isStrategyPRBack", c.isStrategyPRBack());
		createLine(sh, cs, line++, "isStrategyPRForAndBack", c.isStrategyPRForAndBack());
		createLine(sh, cs, line++, "isStrategyGRASP", c.isStrategyGRASP());
	}
	
	private void createLine(Sheet sh, CellStyle cs, int line, String title, String value) {
		Row row = sh.createRow(line);
		ExcelUtils.createCell(row, cs, 0, title);
		ExcelUtils.createCell(row, 1, value);
	}

	private void createLine(Sheet sh, CellStyle cs, int line, String title, int value) {
		Row row = sh.createRow(line);
		ExcelUtils.createCell(row, cs, 0, title);
		ExcelUtils.createCell(row, 1, value);
	}

	private void createLine(Sheet sh, CellStyle cs, int line, String title, double value) {
		Row row = sh.createRow(line);
		ExcelUtils.createCell(row, cs, 0, title);
		ExcelUtils.createCell(row, 1, value);
	}

	private void createLine(Sheet sh, CellStyle cs, int line, String title, boolean value) {
		Row row = sh.createRow(line);
		ExcelUtils.createCell(row, cs, 0, title);
		ExcelUtils.createCell(row, 1, value);
	}

	public CellStyle createCellStype(Sheet sh) {
		CellStyle cs = sh.getWorkbook().createCellStyle();
		Font font = sh.getWorkbook().createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		cs.setFont(font);
		cs.setAlignment(CellStyle.ALIGN_RIGHT);

		return cs;
	}

}
