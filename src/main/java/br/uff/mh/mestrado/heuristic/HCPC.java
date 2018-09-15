package br.uff.mh.mestrado.heuristic;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.gui.listener.EventListener;
import br.uff.mh.mestrado.vo.Results;

public class HCPC extends Greedy {

	public HCPC(Config config, EventListener<Results> listener) {
		super(config, HeuristicType.HCPC, listener);
		this.config.setGreedyRandomLoops(1);
	}
	
	public String getName() {
		return HeuristicType.HCPC.getName();
	}

}
