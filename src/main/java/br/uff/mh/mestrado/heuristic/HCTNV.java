package br.uff.mh.mestrado.heuristic;

import java.util.List;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.gui.listener.EventListener;
import br.uff.mh.mestrado.utils.HeuristicUtils;
import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Results;
import br.uff.mh.mestrado.vo.Subset;

public class HCTNV extends Greedy {

	public HCTNV(Config config, EventListener<Results> listener) {
		super(config, HeuristicType.HCTNV, listener);
	}

	public String getName() {
		return HeuristicType.HCTNV.getName();
	}

	protected Packing run() {
		Packing packing = createNonTrivelSolution();
		List<Subset> copy = collection.copySubset();

		while (!HeuristicUtils.isSubsetDisjoint(collection, packing)) {
			Subset candidate = selectStrategy.select(copy);

			packing.set(candidate.getIndex(), false, candidate.getCost());

			copy.remove(candidate);
		}
		
		if (packing.getCost() > collection.getPacking().getCost())
			collection.setPacking(packing);

		HeuristicUtils.saturation3(collection, packing);
		
		return packing;
	}

	private Packing createNonTrivelSolution() {
		Packing packing = new Packing(collection.size());

		for (int i = 0; i < packing.size(); i++) {
			packing.set(i, true, collection.get(i).getCost());
		}

		return packing;
	}
}
