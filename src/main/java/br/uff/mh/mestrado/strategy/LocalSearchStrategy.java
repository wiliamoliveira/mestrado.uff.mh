package br.uff.mh.mestrado.strategy;

import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Results;

public interface LocalSearchStrategy {
	public Packing findFeasibleNeighbor(Packing curPacking, Results r);
	public int getAdd();
	public int getDel();
}
