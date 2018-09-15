package br.uff.mh.mestrado.heuristic;

import java.util.List;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.vo.Collection;
import br.uff.mh.mestrado.vo.GraspTargetVo;

public abstract class HeuristicDecorator implements Heuristic {
	protected Heuristic heuristic;

	public HeuristicDecorator(Heuristic heuristic) {
		this.heuristic = heuristic;
	}

	public void doJob() throws Exception {
		heuristic.doJob();
	}

	public String taskName() {
		return heuristic.taskName();
	}

	public Collection getCollection() {
		return heuristic.getCollection();
	}

	public Config getConfig() {
		return heuristic.getConfig();
	}

	// public MersenneTwister getRandom() {
	// return heuristic.getRandom();
	// }

	public String getName() {
		return heuristic.getName();
	}

	public List<GraspTargetVo> getGraspTarget() {
		return heuristic.getGraspTarget();
	}
}
