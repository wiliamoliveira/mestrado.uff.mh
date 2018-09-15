package br.uff.mh.mestrado.strategy;

import java.util.Iterator;
import java.util.List;

import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.vo.Subset;

public class SelectByGreaterCostRandom implements SelectStrategy {
	private Heuristic heuristic;

	public SelectByGreaterCostRandom(Heuristic h) {
		this.heuristic = h;
	}

	public Subset select(List<Subset> subset) {
		Iterator<Subset> i = subset.iterator();
		int costFirst = i.next().getCost();

		int count = 1;
		// int add = 0;
		// int maxAdd = (int) (subset.size() * 0.06);
		while (i.hasNext()) {
			if (i.next().getCost() == costFirst)
				count++;
			else {
				// if (add++ == maxAdd)
				break;
			}
		}

		int choosen = heuristic.getRandom().nextInt(count);
		return subset.get(choosen);
	}
}
