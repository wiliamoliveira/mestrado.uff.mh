package br.uff.mh.mestrado.strategy;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.heuristic.HeuristicType;
import br.uff.mh.mestrado.vo.Collection;

public class SelectStrategyFactory {

	public static SelectStrategy create(Config config, Collection c, HeuristicType type, Heuristic heuristic) {
		switch (type) {
		case HCMC:
			return new SelectByGreaterCost();
		case HCMCR:
			return new SelectByGreaterCostRandom(heuristic);
		case HCTNV:
			return new SelectByRandomChoiseToLeave(heuristic);
		case HCMCRAlfa:
			return new SelectByGreaterCostAlfaValue(config, heuristic);
		case HCMCFR:
			return new SelectByGreaterCostFrequencyRandom(c, heuristic);
		case HCPC:
			return new SelectByLowestCost();
		default:
			return new SelectByGreaterCost();
		}
	}

}
