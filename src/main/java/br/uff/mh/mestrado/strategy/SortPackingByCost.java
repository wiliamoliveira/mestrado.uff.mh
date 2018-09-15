package br.uff.mh.mestrado.strategy;

import java.util.Comparator;

import br.uff.mh.mestrado.vo.Packing;

public class SortPackingByCost implements Comparator<Packing> {

	public int compare(Packing p1, Packing p2) {
		return p2.getCost() - p1.getCost();
	}
}
