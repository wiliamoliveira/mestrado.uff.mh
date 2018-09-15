package br.uff.mh.mestrado.heuristic;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.gui.listener.EventListener;
import br.uff.mh.mestrado.vo.Results;

public class HCMCFR extends Greedy {

	public HCMCFR(Config config, EventListener<Results> listener) {
		super(config, HeuristicType.HCMCFR, listener);
	}

	public String getName() {
		return HeuristicType.HCMCFR.getName();
	}
}
