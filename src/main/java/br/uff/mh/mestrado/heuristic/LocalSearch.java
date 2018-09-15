package br.uff.mh.mestrado.heuristic;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.log4j.Logger;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.gui.listener.EventListener;
import br.uff.mh.mestrado.strategy.LocalSearchAddXRemoveYRandom;
import br.uff.mh.mestrado.strategy.LocalSearchStrategy;
import br.uff.mh.mestrado.utils.HeuristicUtils;
import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Results;

public class LocalSearch extends HeuristicDecorator {
	private static Logger logger = Logger.getLogger(LocalSearch.class);

	protected LocalSearchStrategy neighborStrategy;
	protected EventListener<Results> listener;
	protected Config config;
	protected MersenneTwister random;
	protected int add;
	protected int del;
	protected Results r;

	public LocalSearch(int del, int add, Config config, Heuristic heuristic, EventListener<Results> listener) {
		super(heuristic);
		this.listener = listener;
		this.config = config;
		this.del = del;
		this.add = add;
		this.random = new MersenneTwister(config.getSeed());
		this.neighborStrategy = new LocalSearchAddXRemoveYRandom(this, del, add);
		this.r = new Results(taskName());
	}

	private void run() {
		r.updatePackingCost(this.getCollection().getPacking().getCost());

		Packing best = this.getCollection().getPacking();
		Packing curPacking = this.getCollection().getPacking();
		Packing neighbor = neighborStrategy.findFeasibleNeighbor(curPacking, r);

		for (int i = 0; i < 100; i++) {
			while (neighbor.getCost() > curPacking.getCost()) {
				if (neighbor.getCost() > best.getCost())
					best = neighbor;

				curPacking = neighbor;
				neighbor = neighborStrategy.findFeasibleNeighbor(curPacking, r);
			}

			curPacking = best.copy();
			HeuristicUtils.randomMix(this.getCollection(), this.random, curPacking, del * 2);
			neighbor = neighborStrategy.findFeasibleNeighbor(curPacking, r);
		}

		this.getCollection().setPacking(best);
	}

	public void doJob() throws Exception {
		this.r.clear();

		if (logger.isTraceEnabled())
			logger.trace("Starging doJob: " + taskName());

		super.doJob();

		r.startAll();

		run();

		r.endAll();
		listener.update(r);

		if (logger.isTraceEnabled())
			logger.trace("End doJob:" + taskName() + " Packing cost: " + this.getCollection().getPacking().getCost());

		// if (logger.isDebugEnabled())
		// logger.debug("End doJob " + taskName() + " time: " + r.getLoopTotalTime());
	}

	public void setNewSeed(int seed) {
		logger.debug(this.getClass() + " new seed: " + seed);
		this.random = new MersenneTwister(seed);
		heuristic.setNewSeed(seed);
	}

	public String taskName() {
		return super.taskName() + "." + HeuristicType.LS + "(" + del + "-" + add + ")";
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
