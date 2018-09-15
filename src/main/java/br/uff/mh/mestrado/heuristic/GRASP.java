package br.uff.mh.mestrado.heuristic;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.log4j.Logger;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.gui.listener.EventListener;
import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Results;

// fazer comparacao do GRASP-PR com GRASP no tttplot baixar no site do professor

public class GRASP extends HeuristicDecorator {
	private static Logger logger = Logger.getLogger(GRASP.class);

	protected EventListener<Results> listener;
	protected MersenneTwister random;
	protected Config config;
	protected Results r;

	public GRASP(Config config, Heuristic heuristic, EventListener<Results> listener) {
		super(heuristic);
		this.random = new MersenneTwister(config.getSeed());
		this.listener = listener;
		this.config = config;
		this.r = new Results(taskName());
	}

	public void doJob() throws Exception {
		if (logger.isTraceEnabled())
			logger.trace("Starging doJob: " + taskName());

		r.startAll();

		Packing bestPacking = new Packing(config.getMaxCollectionSize());

		for (int i = 1; i <= config.getGraspRandomLoops(); i++) {
			r.setCurrentLoop(i);
			r.startJob();
			listener.update(r);

			super.doJob();
			Packing current = this.getCollection().getPacking();

			r.endJob();

			if (current.getCost() > bestPacking.getCost()) {
				bestPacking = current.copy();
			}

			r.updatePackingCost(current.getCost());
			current.clear();
		}

		this.getCollection().setPacking(bestPacking);
		
		r.updatePackingCost(this.getCollection().getPacking().getCost());
		r.endAll();
		listener.update(r);
		
//		if (logger.isDebugEnabled())
//			logger.debug("End doJob " + taskName() + " time: " + r.getLoopTotalTime());

		if (logger.isTraceEnabled())
			logger.trace("End doJob:" + taskName() + " Packing cost: " + this.getCollection().getPacking().getCost());
	}

	public void setNewSeed(int seed) {
		logger.debug(this.getClass() + " new seed: " + seed);
		this.random = new MersenneTwister(seed);
		heuristic.setNewSeed(seed);
	}

	public String taskName() {
		return super.taskName() + "." + HeuristicType.GRASP;
	}

	public Config getConfig() {
		return this.config;
	}

	public EventListener<Results> getListener() {
		return listener;
	}

	public MersenneTwister getRandom() {
		return this.random;
	}

}
