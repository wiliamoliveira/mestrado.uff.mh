package br.uff.mh.mestrado.strategy;

import java.util.List;

import br.uff.mh.mestrado.vo.Subset;

public class SelectByLowestCost implements SelectStrategy {

	public Subset select(List<Subset> subset) {
		// return the first element
		return subset.get(subset.size()-1);
	}
}
