package br.uff.mh.mestrado.strategy;

import java.util.Comparator;

import br.uff.mh.mestrado.vo.Subset;

public class SortByCostAndSizeAsc implements Comparator<Subset> {
	private static final double FACTOR = 2D;

	public int compare(Subset s1, Subset s2) {
		int sizeS1 = size(s1);
		int sizeS2 = size(s2);

		if (s1.getCost() == s2.getCost()) {
			return sizeS1 - sizeS2;
		} else {
			return s2.getCost() - s1.getCost();
		}

		// if (sizeS1 < (sizeS2 / FACTOR)) {
		// return -1;
		// } else if (sizeS2 > (sizeS1 / FACTOR)) {
		// return 1;
		// } else {
		// return s2.getCost() - s1.getCost();
		// }
	}

	private int size(Subset s) {
		int size = 0;

		for (int i = 0; i < s.size(); i++) {
			if (s.get(i))
				size++;
		}

		return size;
	}
}
