package br.uff.mh.mestrado.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Subset {
	private int index;
	private int cost;
	private boolean[] list;

	public Subset(int cost, int size) {
		this.list = new boolean[size];
		this.cost = cost;
	}

	public void set(int index, boolean value) {
		list[index] = value;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isDisjoint(Subset s) {
		for (int i = 0; i < list.length && i < s.list.length; i++) {
			if (list[i] && s.list[i])
				return false;
		}

		return true;
	}
	
	public int getNumberOfTrues() {
		int count = 0;
		for (int i = 0; i < list.length; i++) {
			if (list[i])
				count++;
		}
		
		return count;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(list).append(index).append(cost);
		return hcb.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (obj == this)
			return true;

		if (obj.getClass() != getClass())
			return false;

		Subset rhs = (Subset) obj;

		EqualsBuilder eb = new EqualsBuilder();
		eb.append(this.list, rhs.list);
		eb.append(this.index, rhs.index);
		eb.append(this.cost, rhs.cost);

		return eb.isEquals();
	}
}
