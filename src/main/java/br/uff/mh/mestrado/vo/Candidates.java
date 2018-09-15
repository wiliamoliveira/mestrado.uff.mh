package br.uff.mh.mestrado.vo;

import java.util.TreeSet;

public class Candidates {

	private TreeSet<Packing> list;
	private int maxSize;

	public Candidates(int maxSize) {
		this.maxSize = maxSize;
		// Comparator<Packing> c = new Comparator<Packing>() {
		// public int compare(Packing o1, Packing o2) {
		// return o1.getCost() - o2.getCost();
		//
		// }
		// };

		this.list = new TreeSet<Packing>();
	}

	public boolean tryAdd(Packing p) {
		boolean ret = false;
		if (list.size() < maxSize) {
			list.add(p.copy());
			ret = true;
		} else {
			if (!list.contains(p) && list.first().getCost() < p.getCost()) {
				list.pollFirst();
				list.add(p.copy());
				ret = true;
			}
		}

		return ret;
	}
	
	public Packing getLast() {
		return list.last();
	}

	public Packing get(int index) {
		Packing r = null;
		int i = 0;

		for (Packing packing : list) {
			if (i == index) {
				r = packing;
				break;
			}

			i++;
		}

		return r;
	}

	public int size() {
		return list.size();
	}

	public void clear() {
		list.clear();
	}
}
