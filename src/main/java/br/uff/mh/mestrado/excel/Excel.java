package br.uff.mh.mestrado.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.utils.TextBuilder;
import br.uff.mh.mestrado.vo.ResultsVo;

public class Excel {
	private static Logger logger = Logger.getLogger(Excel.class);
	private FileOutputStream file;
	private SXSSFWorkbook wb;
	private List<Heuristic> listHeuristic;
	private Config c;
	private List<ResultsVo> results;

	public Excel(Config c) {
		this.listHeuristic = new ArrayList<Heuristic>();
		this.c = c;
		this.results = new ArrayList<ResultsVo>();
	}

	public void add(Heuristic h) {
		listHeuristic.add(h);
	}

	public void addResult(ResultsVo r) {
		results.add(r);
	}

	public void create(String filename) {
		logger.info("Creating workbook [" + filename + "]");
		open(filename);

		SheetInput si = new SheetInput();
		SheetResult sr = new SheetResult();
		SheetCollection sc = new SheetCollection();
		SheetGraspTarget sgt = new SheetGraspTarget();

		si.create(this, c);
		sr.create(this, results);
		sc.create(this, c, listHeuristic);
		sgt.create(this, c, listHeuristic);

		close();

		listHeuristic.clear();
		logger.info("Workbook [" + filename + "] has been created.");
	}

	public Sheet createSheet(String name) {
		return wb.createSheet(name);
	}

	private void open(String filename) {
		try {
			file = new FileOutputStream(filename);
			wb = new SXSSFWorkbook(500000);
		} catch (FileNotFoundException e) {
			logger.error(TextBuilder.getFistFrameOfThrowable(e));
		}
	}

	private void close() {
		try {
			wb.write(file);
			file.close();
			wb.dispose();
		} catch (IOException e) {
			logger.error(TextBuilder.getFistFrameOfThrowable(e));
		}
	}
}
