package br.uff.mh.mestrado.strategy;

import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.heuristic.HeuristicType;
import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Results;

public class PathRelinkingForwardAndBackward implements PathRelinkingStrategy {
	private PathRelinkingStrategy forwardStrategy;
	private PathRelinkingStrategy backwardtrategy;

	public PathRelinkingForwardAndBackward(Heuristic heuristic) {
		this.forwardStrategy = PathRelinkingStrategyFactory.create(HeuristicType.PRFor, heuristic);
		this.backwardtrategy = PathRelinkingStrategyFactory.create(HeuristicType.PRBack, heuristic);
	}

	public Packing findFeasibleNeighbor(Packing initialSolution, Packing targetSolution, Results r) {
		Packing fw = forwardStrategy.findFeasibleNeighbor(initialSolution, targetSolution, r);
		Packing bw = backwardtrategy.findFeasibleNeighbor(initialSolution, targetSolution, r);

		return fw.getCost() > bw.getCost() ? fw : bw;
	}
}
