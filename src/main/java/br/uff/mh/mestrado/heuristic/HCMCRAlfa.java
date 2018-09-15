package br.uff.mh.mestrado.heuristic;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.gui.listener.EventListener;
import br.uff.mh.mestrado.vo.Results;

public class HCMCRAlfa extends Greedy {

	public HCMCRAlfa(Config config, EventListener<Results> listener) {
		super(config, HeuristicType.HCMCRAlfa, listener);
	}

	public String getName() {
		return HeuristicType.HCMCRAlfa.getName();
	}
}
