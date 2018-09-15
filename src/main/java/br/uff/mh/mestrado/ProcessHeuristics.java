package br.uff.mh.mestrado;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.uff.mh.mestrado.exception.CostMismatchException;
import br.uff.mh.mestrado.exception.IndexMismatchException;
import br.uff.mh.mestrado.exception.PackingIsNotDisjointException;
import br.uff.mh.mestrado.gui.MainController;
import br.uff.mh.mestrado.gui.listener.EventListener;
import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.utils.HeuristicUtils;
import br.uff.mh.mestrado.utils.SoundUtils;
import br.uff.mh.mestrado.utils.TextBuilder;

public class ProcessHeuristics implements Runnable {

	private static Logger logger = Logger.getLogger(ProcessHeuristics.class);

	private List<Heuristic> list;
	private EventListener<String> listener;
	private MainController mc;

	public ProcessHeuristics(EventListener<String> listener, MainController mc) {
		this.list = new ArrayList<Heuristic>();
		this.listener = listener;
		this.mc = mc;
	}

	public void add(Heuristic h) {
		list.add(h);
	}

	public void run() {
		String message = "";
		try {
			listener.update("Starting...");

			for (Heuristic heuristic : list) {
				heuristic.doJob();
				HeuristicUtils.checkfeasibility(heuristic);
				HeuristicUtils.checkCost(heuristic);
				HeuristicUtils.checkSubsetIndex(heuristic);
			}

			message = "Finished...";
		} catch (PackingIsNotDisjointException e) {
			message = "Error! " + e.getMessage();
			logger.debug(TextBuilder.getFistFrameOfThrowable(e));
			e.printStackTrace();
		} catch (CostMismatchException e) {
			message = "Error! " + e.getMessage();
			logger.debug(TextBuilder.getFistFrameOfThrowable(e));
			e.printStackTrace();
		} catch (IndexMismatchException e) {
			message = "Error! " + e.getMessage();
			logger.debug(TextBuilder.getFistFrameOfThrowable(e));
			e.printStackTrace();
		} catch (Exception e) {
			message = "Error! " + e.getMessage();
			logger.debug(TextBuilder.getFistFrameOfThrowable(e));
			e.printStackTrace();
		} finally {
			mc.setDisableButtons(false);
			SoundUtils.getInstance().play();
			listener.update(message);
		}
	}
}
