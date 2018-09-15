
package br.uff.mh.mestrado.heuristic;

import java.util.List;

import org.apache.commons.math3.random.MersenneTwister;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.gui.listener.EventListener;
import br.uff.mh.mestrado.vo.Collection;
import br.uff.mh.mestrado.vo.GraspTargetVo;
import br.uff.mh.mestrado.vo.Results;

public interface Heuristic {
	public void doJob() throws Exception;
	public String taskName();
	public Collection getCollection();
	public Config getConfig();
	public MersenneTwister getRandom();
	public void setNewSeed(int seed);
	public String getName();
	public EventListener<Results> getListener();
	public List<GraspTargetVo> getGraspTarget();
}
