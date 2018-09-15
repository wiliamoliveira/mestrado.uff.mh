package br.uff.mh.mestrado.heuristic;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.gui.listener.EventListener;
import br.uff.mh.mestrado.vo.Results;

public class HCMCR extends Greedy {

	public HCMCR(Config config, EventListener<Results> listener) {
		super(config, HeuristicType.HCMCR, listener);
	}

	public String getName() {
		return HeuristicType.HCMCR.getName();
	}
}
