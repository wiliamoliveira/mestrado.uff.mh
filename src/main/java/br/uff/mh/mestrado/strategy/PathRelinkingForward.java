package br.uff.mh.mestrado.strategy;

import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.utils.HeuristicUtils;
import br.uff.mh.mestrado.vo.Collection;
import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Results;
import br.uff.mh.mestrado.vo.Subset;

public class PathRelinkingForward implements PathRelinkingStrategy {
	private Heuristic heuristic;

	public PathRelinkingForward(Heuristic heuristic) {
		this.heuristic = heuristic;
	}

	// should check if diff > 3
	public Packing findFeasibleNeighbor(Packing initialS, Packing targetS, Results r) {
		Collection c = heuristic.getCollection();
		Packing initS = initialS.copy();
		Packing curS = initS.copy();
		Packing bestS = initialS.copy();

		for (int i = 0; i < initS.size(); i++) {
			r.setCurrentLoop(i);
			r.startJob();

			if (initS.get(i) != targetS.get(i)) {
				Subset candidate = c.get(i);
				// boolean isFeasible = HeuristicUtils.isSubsetDisjoint(c, initS, candidate);
				initS.set(i, targetS.get(i), candidate.getCost());

				curS = initS.copy();
				HeuristicUtils.makeFeasible(heuristic.getCollection(), curS);
//				if (!HeuristicUtils.isSubsetDisjoint(c, curS)) {
//					HeuristicUtils.repair(heuristic.getCollection(), curS);
//				}
//
//				HeuristicUtils.saturation(heuristic.getCollection(), curS);

				if (curS.getCost() > bestS.getCost())
					bestS = curS;
			}

			r.endJob();
			r.updatePackingCost(curS.getCost());
			heuristic.getListener().update(r);
		}

		return bestS;
	}

}
