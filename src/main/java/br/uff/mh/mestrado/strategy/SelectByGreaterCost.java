package br.uff.mh.mestrado.strategy;

import java.util.List;

import br.uff.mh.mestrado.vo.Subset;

public class SelectByGreaterCost implements SelectStrategy {

	public Subset select(List<Subset> subset) {
		// return the first element
		return subset.get(0);
	}
}
