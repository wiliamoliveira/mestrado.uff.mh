package br.uff.mh.mestrado.heuristic;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.log4j.Logger;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.gui.listener.EventListener;
import br.uff.mh.mestrado.strategy.PathRelinkingStrategy;
import br.uff.mh.mestrado.strategy.PathRelinkingStrategyFactory;
import br.uff.mh.mestrado.vo.Candidates;
import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Results;

public class PathRelinking extends HeuristicDecorator {
	private static Logger logger = Logger.getLogger(PathRelinking.class);

	protected EventListener<Results> listener;
	protected Config config;
	protected PathRelinkingStrategy prStrategy;
	protected HeuristicType myType;
	protected MersenneTwister random;
	protected Results r;

	public PathRelinking(Heuristic heuristic, HeuristicType type, Config config, EventListener<Results> listener) {
		super(heuristic);
		this.listener = listener;
		this.config = config;
		this.random = new MersenneTwister(config.getSeed());
		this.prStrategy = PathRelinkingStrategyFactory.create(type, this);
		this.myType = type;
		this.r = new Results(taskName());

	}

	private Packing findFeasibleNeighbor(Packing c1, Packing c2, Results r) {
		if (c1.getCost() > c2.getCost())
			return prStrategy.findFeasibleNeighbor(c1, c2, r);
		else
			return prStrategy.findFeasibleNeighbor(c2, c1, r);
	}

	private Packing selectBest(Packing current, Packing randomCandidate, Packing pr) {
		Candidates candidates = this.getCollection().getPackingCandidates();

		if (logger.isDebugEnabled()) {
			logger.debug("current: " + current.getCost() + " randomCandidate: " + randomCandidate.getCost() + " pr: " + pr.getCost());
		}

		int costBefore = candidates.getLast().getCost();
		candidates.tryAdd(pr);
		candidates.tryAdd(current);
		int costAfter = candidates.getLast().getCost();

		if (logger.isDebugEnabled()) {
			printCandidates("Nova Lista: ");
			if (costAfter > costBefore && pr.getCost() > current.getCost()) {
				logger.debug("PR Melhorou: " + costAfter);
			}
		}

		return candidates.getLast().copy();
	}

	public void doJob() throws Exception {
		this.r.clear();

		super.doJob();

		r.startAll();

		Packing current = this.getCollection().getPacking();
		Candidates candidates = this.getCollection().getPackingCandidates();

		if (candidates.size() <= 0) {
			candidates.tryAdd(current.copy());
			printCandidates("Candidates: ");
		} else {
			int maxIndex = Math.min(config.getPathRelinkingCandidateListMaxSize(), candidates.size());
			int indexRandomCandidate = this.getRandom().nextInt(maxIndex);
			Packing randomCandidate = candidates.get(indexRandomCandidate);

			Packing pr = findFeasibleNeighbor(current, randomCandidate, r);
			Packing bestPacking = selectBest(current, randomCandidate, pr);

//			 bestPacking = pathRelinkingAllCandidates(bestPacking);

			this.getCollection().setPacking(bestPacking);
		}

		r.updatePackingCost(this.getCollection().getPacking().getCost());
		r.endAll();
		listener.update(r);
	}

	private Packing pathRelinkingAllCandidates(Packing bestPacking) {
		Packing myBest = bestPacking;
		Candidates candidates = this.getCollection().getPackingCandidates();

		for (int i = 0; i < candidates.size(); i++) {
			Packing c1 = candidates.get(i);

			for (int j = i + 1; j < candidates.size(); j++) {
				Packing c2 = candidates.get(j);

				Packing pr = findFeasibleNeighbor(c1, c2, r);
				candidates.tryAdd(pr);

				if (pr.getCost() > myBest.getCost())
					myBest = pr;
			}
		}

		if (logger.isDebugEnabled()) {
			if (myBest.getCost() > bestPacking.getCost()) {
				logger.debug("Found better packing in PR-ALL. old-best: " + bestPacking.getCost() + " new-best:" + myBest.getCost());
			}
		}

		return myBest;
	}

	private void printCandidates(String text) {
		Candidates candidates = this.getCollection().getPackingCandidates();

		if (logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append(text);
			for (int i = 0; i < candidates.size(); i++) {
				sb.append(candidates.get(i).getCost());
				sb.append(", ");
			}

			logger.debug(sb);
		}
	}

	public void setNewSeed(int seed) {
		logger.debug(this.getClass() + " new seed: " + seed);
		this.random = new MersenneTwister(seed);
		heuristic.setNewSeed(seed);
	}

	public String taskName() {
		return super.taskName() + "." + myType;
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
