package br.uff.mh.mestrado.vo;

import java.util.Arrays;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class Packing implements Comparable<Packing> {
	private boolean[] list;
	private int cost;

	public Packing(int size) {
		this.list = new boolean[size];
	}

	public void clear() {
		int size = list.length;
		this.list = new boolean[size];
		this.cost = 0;
	}

	public void set(int index, boolean value, int cost) {
		boolean before = this.list[index];

		this.list[index] = value;

		if (!before && value)
			this.cost += cost;
		else if (before && !value)
			this.cost -= cost;
	}

	public boolean get(int index) {
		return list[index];
	}

	public int size() {
		return list.length;
	}

	public int getCost() {
		return cost;
	}

	public Packing copy() {
		Packing copy = new Packing(list.length);
		copy.cost = this.cost;
		copy.list = Arrays.copyOf(list, list.length);

		return copy;
	}

	// public String toString() {
	// return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
	// }

	public String toString() {
		return Integer.toString(cost);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(list);
	}

	@Override
	public boolean equals(Object obj) {
		Packing other = (Packing) obj;
		if (!Arrays.equals(list, other.list))
			return false;
		return true;
	}

	public int compareTo(Packing o) {
//		return new CompareToBuilder().append(this.list, o.list).append(this.cost, o.cost).build();
		return new CompareToBuilder().append(this.cost, o.cost).build();
	}

}
