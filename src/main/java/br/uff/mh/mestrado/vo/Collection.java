package br.uff.mh.mestrado.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import br.uff.mh.mestrado.strategy.SortByCost;

public class Collection {

	private List<Subset> collection;
	private Packing packing;
	private Candidates packingCandidates;

	public Collection(int numberOfSubsets, int numberOfItems, int maxPackingCandidatesSize) {
		this.collection = new ArrayList<Subset>(numberOfSubsets);
		this.packing = new Packing(numberOfSubsets);
		this.packingCandidates = new Candidates(maxPackingCandidatesSize);
	}

	public Candidates getPackingCandidates() {
		return packingCandidates;
	}

	public Packing getPacking() {
		return packing;
	}

	public List<Subset> getCollection() {
		return collection;
	}

	public void add(Subset subset) {
		subset.setIndex(collection.size());
		collection.add(subset);
	}

	public Subset get(int index) {
		return collection.get(index);
	}

	public int size() {
		return collection.size();
	}

	public void sort() {
		Collections.sort(this.collection, new SortByCost());

		int index = 0;
		for (Subset subset : collection) {
			subset.setIndex(index++);
		}
	}

	public List<Subset> copySubset() {
		return new LinkedList<Subset>(collection);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
	}

	public void setPacking(Packing packing) {
		this.packing = packing;

	}
}
