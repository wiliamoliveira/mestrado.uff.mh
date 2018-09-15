package br.uff.mh.mestrado.excel;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.utils.ExcelUtils;
import br.uff.mh.mestrado.vo.GraspTargetVo;

public class SheetGraspTarget {
	private static Logger logger = Logger.getLogger(SheetGraspTarget.class);

	public void create(Excel excel, Config c, List<Heuristic> list) {
		logger.debug("Creating Sheet Collection");

		for (Heuristic heuristic : list) {
			Sheet sh = excel.createSheet(heuristic.getName() + "_GT");

			createHeader(sh, heuristic);
			createGraspTarget(sh, heuristic);
		}
	}

	private void createHeader(Sheet sh, Heuristic h) {
		Row row = sh.createRow(0);
		int col = 0;
		ExcelUtils.createCell(row, col++, "cost");
		ExcelUtils.createCell(row, col++, "time");
		ExcelUtils.createCell(row, col++, "loop");
	}

	private void createGraspTarget(Sheet sh, Heuristic heuristic) {
		List<GraspTargetVo> list = heuristic.getGraspTarget();

		int iRow = 1;
		for (GraspTargetVo vo : list) {
			Row row = sh.createRow(iRow++);

			int col = 0;
			ExcelUtils.createCell(row, col++, vo.getCost());
			ExcelUtils.createCell(row, col++, vo.getTime());
			ExcelUtils.createCell(row, col++, vo.getLoop());
		}
	}
}
