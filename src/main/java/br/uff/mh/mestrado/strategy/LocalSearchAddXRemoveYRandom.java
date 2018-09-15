package br.uff.mh.mestrado.strategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.utils.HeuristicUtils;
import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Results;
import br.uff.mh.mestrado.vo.Subset;

public class LocalSearchAddXRemoveYRandom implements LocalSearchStrategy {
	private static Logger logger = Logger.getLogger(LocalSearchAddXRemoveYRandom.class);

	private Heuristic heuristic;
	private int add;
	private int del;

	public LocalSearchAddXRemoveYRandom(Heuristic heuristic, int del, int add) {
		this.heuristic = heuristic;
		this.del = del;
		this.add = add;
	}

	public Packing findFeasibleNeighbor(Packing curPacking, Results r) {
		boolean found = false;

		Packing neighbor = null;

		if (logger.isTraceEnabled())
			logger.trace("Add: " + add + " Rem: " + del);

		for (int i = 0; i < heuristic.getConfig().getLocalSearchRandomLoops(); i++) {
			r.setCurrentLoop(i);
			r.startJob();

			neighbor = nextNeighbor(curPacking);

			if (neighbor.getCost() > curPacking.getCost()) {
				found = true;

				r.endJob();
				r.updatePackingCost(neighbor.getCost());

				break;
			} else {
				r.endJob();
				heuristic.getListener().update(r);
			}
		}

		if (!found) {
			r.endJob();
			r.updatePackingCost(curPacking.getCost());
			heuristic.getListener().update(r);
		}

		return (found ? neighbor : curPacking);
	}

	private Packing nextNeighbor(Packing packing) {
		Packing neighbor = packing.copy();

		List<Integer> candidatesToBeRemoved = generateList(neighbor, true);
		if (candidatesToBeRemoved.size() > 0) {
			for (int i = 0; i < del; i++) {
				int choosen = heuristic.getRandom().nextInt(candidatesToBeRemoved.size());
				int index = candidatesToBeRemoved.get(choosen);
				neighbor.set(index, false, heuristic.getCollection().get(index).getCost());
			}
		}

		List<Integer> candidatesToBeAdded = generateListFeasible(neighbor);
		if (candidatesToBeAdded.size() > 0) {
			for (int i = 0; i < add; i++) {
				int choosen = heuristic.getRandom().nextInt(candidatesToBeAdded.size());
				int index = candidatesToBeAdded.get(choosen);
				Subset subset = heuristic.getCollection().get(index);

				neighbor.set(index, true, subset.getCost());

				candidatesToBeAdded.remove(choosen);
				candidatesToBeAdded = generateListFeasible(neighbor, candidatesToBeAdded);
				if (candidatesToBeAdded.size() == 0)
					break;
			}
		}

		return neighbor;
	}

	private List<Integer> generateList(Packing packing, boolean status) {
		List<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < packing.size(); i++) {
			boolean item = packing.get(i);

			if (item == status)
				list.add(i);
		}

		return list;
	}

	private List<Integer> generateListFeasible(Packing packing) {
		List<Integer> list = new LinkedList<Integer>();

		for (int i = 0; i < packing.size(); i++) {
			boolean item = packing.get(i);

			if (!item) {
				if (HeuristicUtils.isSubsetDisjoint(heuristic.getCollection(), packing, heuristic.getCollection().get(i)))
					list.add(i);
			}

		}

		return list;
	}

	private List<Integer> generateListFeasible(Packing packing, List<Integer> candidates) {
		List<Integer> list = new LinkedList<Integer>();

		for (Integer index : candidates) {
			Subset subset = heuristic.getCollection().get(index);
			if (HeuristicUtils.isSubsetDisjoint(heuristic.getCollection(), packing, subset))
				list.add(index);
		}

		return list;
	}

	public int getAdd() {
		return add;
	}

	public int getDel() {
		return del;
	}
}
