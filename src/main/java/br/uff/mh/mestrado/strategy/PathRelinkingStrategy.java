package br.uff.mh.mestrado.strategy;

import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Results;

public interface PathRelinkingStrategy {
	public Packing findFeasibleNeighbor(Packing initialSolution, Packing targetSolution, Results r);
}
