package br.uff.mh.mestrado.strategy;

import java.util.List;

import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.vo.Subset;

public class SelectByRandomChoiseToLeave implements SelectStrategy {
	private Heuristic heuristic;

	public SelectByRandomChoiseToLeave(Heuristic h) {
		this.heuristic = h;
	}

	public Subset select(List<Subset> subset) {
		int choosen = heuristic.getRandom().nextInt(subset.size());
		return subset.get(choosen);
	}
}
