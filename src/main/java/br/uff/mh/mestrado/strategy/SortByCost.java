package br.uff.mh.mestrado.strategy;

import java.util.Comparator;

import br.uff.mh.mestrado.vo.Subset;

public class SortByCost implements Comparator<Subset> {

	public int compare(Subset o1, Subset o2) {
		return o2.getCost() - o1.getCost();
	}
}
