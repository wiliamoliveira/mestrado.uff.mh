package br.uff.mh.mestrado.strategy;

import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.utils.HeuristicUtils;
import br.uff.mh.mestrado.vo.Packing;

public class GeneticCrossingUniform implements GeneticCrossingStrategy {
	private Heuristic heuristic;

	public GeneticCrossingUniform(Heuristic h) {
		this.heuristic = h;
	}

	public Packing crossing(Packing father1, Packing father2) {
		Packing packing = father1.copy();

		for (int i = 0; i < father1.size(); i++) {
			if (!heuristic.getRandom().nextBoolean()) {
				packing.set(i, father2.get(i), heuristic.getCollection().get(i).getCost());
			}
		}
		
		HeuristicUtils.makeFeasible(heuristic.getCollection(), packing);

		return packing;
	}
}
