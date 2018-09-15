package br.uff.mh.mestrado.strategy;

import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.heuristic.HeuristicType;

public class PathRelinkingStrategyFactory {

	public static PathRelinkingStrategy create(HeuristicType type, Heuristic heuristic) {
		switch (type) {
		case PRFor:
			return new PathRelinkingForward(heuristic);
		case PRBack:
			return new PathRelinkingBackward(heuristic);
		case PRForAndBack:
			return new PathRelinkingForwardAndBackward(heuristic);
		default:
			return null;
		}
	}

}
