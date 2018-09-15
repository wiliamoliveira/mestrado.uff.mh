package br.uff.mh.mestrado.heuristic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.log4j.Logger;

import br.uff.mh.mestrado.CollectionFactory;
import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.gui.listener.EventListener;
import br.uff.mh.mestrado.strategy.SelectStrategy;
import br.uff.mh.mestrado.strategy.SelectStrategyFactory;
import br.uff.mh.mestrado.utils.HeuristicUtils;
import br.uff.mh.mestrado.vo.Collection;
import br.uff.mh.mestrado.vo.GraspTargetVo;
import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Results;
import br.uff.mh.mestrado.vo.Subset;

public abstract class Greedy implements Heuristic {

	private static Logger logger = Logger.getLogger(Greedy.class);

	protected Config config;
	protected HeuristicType type;
	protected MersenneTwister random;
	protected Collection collection;
	protected SelectStrategy selectStrategy;
	protected EventListener<Results> listener;
	protected List<GraspTargetVo> gtlist;
	protected Results r;

	public Greedy(Config config, HeuristicType type, EventListener<Results> listener) {
		this.config = config;
		this.type = type;
		this.random = new MersenneTwister(config.getSeed());
		this.listener = listener;
		this.collection = CollectionFactory.createCollection(config, new MersenneTwister(config.getGreedySeed()));
		this.selectStrategy = SelectStrategyFactory.create(config, collection, type, this);
		this.gtlist = new ArrayList<GraspTargetVo>(config.getGraspTargetLoops());
		this.r = new Results(taskName());
	}

	protected Packing run() {
		Packing packing = new Packing(collection.size());
		List<Subset> copy = collection.copySubset();

		while (copy.size() > 0) {
			Subset candidate = selectStrategy.select(copy);

			if (HeuristicUtils.isSubsetDisjoint(collection, packing, candidate)) {
				packing.set(candidate.getIndex(), true, candidate.getCost());
			}

			copy.remove(candidate);
		}

		if (packing.getCost() > collection.getPacking().getCost())
			collection.setPacking(packing);

		return packing;

	}

	public void doJob() {
		this.r.clear();
		
		
		
		r.startAll();

		for (int i = 1; i <= config.getGreedyRandomLoops(); i++) {
			if (logger.isTraceEnabled())
				logger.trace("Starging doJob (" + i + "): " + taskName());

			r.setCurrentLoop(i);
			r.startJob();

			Packing packing = run();

			r.endJob();
			r.updatePackingCost(packing.getCost());

			listener.update(r);

			if (logger.isTraceEnabled())
				logger.trace("End doJob (" + i + "): " + taskName() + " Packing cost: " + packing.getCost());
		}

		r.endAll();
		listener.update(r);
		
//		if (logger.isDebugEnabled())
//			logger.debug("End doJob " + taskName() + " time: " + r.getLoopTotalTime());
	}

	public void setNewSeed(int seed) {
		logger.debug(this.getClass() + " new seed: " + seed);
		this.random = new MersenneTwister(seed);
	}

	public String taskName() {
		return this.type.getName();
	}

	public Collection getCollection() {
		return this.collection;
	}

	public Config getConfig() {
		return this.config;
	}

	public MersenneTwister getRandom() {
		return this.random;
	}

	public EventListener<Results> getListener() {
		return listener;
	}

	public List<GraspTargetVo> getGraspTarget() {
		return gtlist;
	}
}
