package br.uff.mh.mestrado.strategy;

import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.heuristic.HeuristicType;
import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Results;

public class PathRelinkingBackward implements PathRelinkingStrategy {
	private PathRelinkingStrategy forwardStrategy;

	public PathRelinkingBackward(Heuristic heuristic) {
		this.forwardStrategy = PathRelinkingStrategyFactory.create(HeuristicType.PRFor, heuristic);
	}

	public Packing findFeasibleNeighbor(Packing initialSolution, Packing targetSolution, Results r) {
		return forwardStrategy.findFeasibleNeighbor(targetSolution, initialSolution, r);
	}

}
