package br.uff.mh.mestrado.strategy;

import java.util.List;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.vo.Subset;

public class SelectByGreaterCostAlfaValue implements SelectStrategy {
	private Heuristic heuristic;
	private Config config;

	public SelectByGreaterCostAlfaValue(Config config, Heuristic h) {
		this.config = config;
		this.heuristic = h;
	}

	// 0.2 -> 727 e 775
	// 0.8 -> 591 e 722
	public Subset select(List<Subset> subset) {
		if (config.getAlfa() >= 1)
			return subset.get(0);

		double rcl = 1 - config.getAlfa();

		if (rcl == 0)
			return subset.get(0);

		int numberOfCandidates = (int) (subset.size() * rcl);
		if (numberOfCandidates == 0)
			return subset.get(0);

		int choosen = heuristic.getRandom().nextInt(numberOfCandidates);
		return subset.get(choosen);
	}
}
