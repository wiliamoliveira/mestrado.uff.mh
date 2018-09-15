package br.uff.mh.mestrado.heuristic;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.log4j.Logger;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.gui.listener.EventListener;
import br.uff.mh.mestrado.utils.DateUtils;
import br.uff.mh.mestrado.utils.HeuristicUtils;
import br.uff.mh.mestrado.vo.GraspTargetVo;
import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Results;

// fazer comparacao do GRASP-PR com GRASP no tttplot baixar no site do professor

public class GRASPTarget extends HeuristicDecorator {
	private static Logger logger = Logger.getLogger(GRASPTarget.class);

	protected EventListener<Results> listener;
	protected MersenneTwister random;
	protected Config config;
	protected long loop;
	protected int loopGrasp;

	public GRASPTarget(Config config, Heuristic heuristic, EventListener<Results> listener) {
		super(heuristic);
		this.random = new MersenneTwister(config.getSeed());
		this.listener = listener;
		this.config = config;
	}

	private void run() throws Exception {
		if (logger.isTraceEnabled())
			logger.trace("Starging doJob: " + taskName());

		Results r = new Results(taskName());
		r.startAll();

		Packing bestPacking = new Packing(config.getMaxCollectionSize());

		loopGrasp = 1;
		while (bestPacking.getCost() < config.getGraspTarget() && r.getElapsedTimeTillNow() < config.getGraspTargetMaxTime()) {
			r.setCurrentLoop(loopGrasp++);
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

		if (logger.isTraceEnabled())
			logger.trace("End doJob:" + taskName() + " Packing cost: " + this.getCollection().getPacking().getCost());
	}

	public void doJob() throws Exception {
		for (int i = 1; i <= config.getGraspTargetLoops(); i++) {
			this.getCollection().getPackingCandidates().clear();
			this.getCollection().getPacking().clear();
			this.setNewSeed(i);
			this.loop = i;

			long start = System.nanoTime();
			run();
			long end = DateUtils.getElapsedTimeInSecs(start);
			
			HeuristicUtils.checkfeasibility(heuristic);
			HeuristicUtils.checkCost(heuristic);
			HeuristicUtils.checkSubsetIndex(heuristic);

			GraspTargetVo vo = new GraspTargetVo(this.getCollection().getPacking().getCost(), end, loopGrasp);
			this.getGraspTarget().add(vo);

			if (logger.isDebugEnabled()) {
				logger.debug("GraspTarger loop: " + loop + " found: " + vo.toString());
			}
		}

	}

	public void setNewSeed(int seed) {
		logger.debug(this.getClass() + " new seed: " + seed);
		this.random = new MersenneTwister(seed);
		heuristic.setNewSeed(seed);
	}

	public String taskName() {
		return super.taskName() + "." + HeuristicType.GRASPTarget + "(" + loop + ")";
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
