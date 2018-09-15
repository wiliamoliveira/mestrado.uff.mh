package br.uff.mh.mestrado.strategy;

import java.util.List;

import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.utils.HeuristicUtils;
import br.uff.mh.mestrado.vo.Collection;
import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Results;
import br.uff.mh.mestrado.vo.Subset;

public class PathRelinkingForOLD2 implements PathRelinkingStrategy {
	private Heuristic heuristic;

	public PathRelinkingForOLD2(Heuristic heuristic) {
		this.heuristic = heuristic;
	}

	private void makeStep(List<Integer> diff, Packing pathRelinking) {
		Collection c = heuristic.getCollection();

		int bestCost = 0;
		int bestCandidateIndex = -1;
		int bestJ = -1;
		for (int j = 0; j < diff.size(); j++) {
			int index = diff.get(j);
			Subset candidate = c.get(index);

			boolean item = pathRelinking.get(index);

			if (item) {
				// will be set to 0, so feasibility is guaranteed. Just check costs
				int costToUseCandidate = pathRelinking.getCost() - candidate.getCost();
				if (costToUseCandidate > bestCost) {
					bestCost = costToUseCandidate;
					bestCandidateIndex = index;
					bestJ = j;
				}
			} else {
				// will be set to 1, so need to check feasibility
				if (HeuristicUtils.isSubsetDisjoint(c, pathRelinking, candidate)) {
					int costToUseCandidate = pathRelinking.getCost() + candidate.getCost();
					if (costToUseCandidate > bestCost) {
						bestCost = costToUseCandidate;
						bestCandidateIndex = index;
						bestJ = j;
					}
				}
			}
		}

		if (diff.size() == 1) {
			bestCandidateIndex = diff.get(0);
			bestJ = 0;
		}

		if (bestCandidateIndex >= 0) {
			boolean item = pathRelinking.get(bestCandidateIndex);
			pathRelinking.set(bestCandidateIndex, !item, c.get(bestCandidateIndex).getCost());

			diff.remove(bestJ);
		}
	}

	// should check if diff > 3
	public Packing findFeasibleNeighbor(Packing initialSolution, Packing targetSolution, Results r) {
		List<Integer> diff = HeuristicUtils.diff(initialSolution, targetSolution);
		Packing pathRelinking = initialSolution.copy();
		Packing bestPacking = initialSolution.copy();

		int i = 1;
		while (diff.size() > 0) {
			r.setCurrentLoop(i++);
			r.startJob();

			makeStep(diff, pathRelinking);

			if (pathRelinking.getCost() > bestPacking.getCost()) {
				bestPacking = pathRelinking.copy();
			}

			r.endJob();
			r.updatePackingCost(pathRelinking.getCost());
			heuristic.getListener().update(r);
		}

		return bestPacking;
	}

}
