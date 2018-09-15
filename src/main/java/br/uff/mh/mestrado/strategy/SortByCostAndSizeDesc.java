package br.uff.mh.mestrado.strategy;

import java.util.Comparator;

import br.uff.mh.mestrado.vo.Subset;

public class SortByCostAndSizeDesc implements Comparator<Subset> {
	private SortByCostAndSizeAsc sort;

	public SortByCostAndSizeDesc() {
		this.sort = new SortByCostAndSizeAsc();
	}

	public int compare(Subset s1, Subset s2) {
		return this.sort.compare(s2, s1);
	}
}
