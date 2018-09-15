package br.uff.mh.mestrado.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.random.MersenneTwister;

import br.uff.mh.mestrado.exception.CostMismatchException;
import br.uff.mh.mestrado.exception.IndexMismatchException;
import br.uff.mh.mestrado.exception.PackingIsNotDisjointException;
import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.strategy.SortByCostAndSizeAsc;
import br.uff.mh.mestrado.strategy.SortByCostAndSizeDesc;
import br.uff.mh.mestrado.vo.Collection;
import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Subset;

public class HeuristicUtils {

	public static void randomMix(Collection c, MersenneTwister random, Packing p, int max) {
		List<Subset> list = getMostRelevantItens(c, p, true);

		for (int i = 0; i < max; i++) {
			int index = random.nextInt(list.size());
			Subset s = list.get(index);
			p.set(s.getIndex(), false, s.getCost());
		}
	}

	public static List<Integer> diff(Packing p1, Packing p2) {
		List<Integer> diff = new ArrayList<Integer>();

		for (int i = 0; i < p1.size(); i++) {
			if (p1.get(i) != p2.get(i))
				diff.add(i);
		}

		return diff;
	}

	public static boolean isSubsetDisjoint(Collection c, Packing packing, Subset candidate) {
		for (int i = 0; i < packing.size(); i++) {
			if (packing.get(i) && i != candidate.getIndex()) {
				if (!c.get(i).isDisjoint(candidate))
					return false;
			}
		}

		return true;
	}

	public static boolean isSubsetDisjoint(Collection c, Packing packing) {
		for (int i = 0; i < packing.size(); i++) {
			if (packing.get(i)) {
				Subset subset1 = c.get(i);

				for (int j = i + 1; j < packing.size(); j++) {
					if (packing.get(j)) {
						Subset subset2 = c.get(j);

						if (!subset1.isDisjoint(subset2))
							return false;
					}
				}
			}
		}

		return true;
	}

	public static void checkfeasibility(Heuristic h) throws PackingIsNotDisjointException {
		Collection c = h.getCollection();
		if (!HeuristicUtils.isSubsetDisjoint(c, c.getPacking()))
			throw new PackingIsNotDisjointException(h.getName());
	}

	public static void checkCost(Heuristic h) throws CostMismatchException {
		Collection c = h.getCollection();
		int totalCost = 0;

		for (int i = 0; i < c.size(); i++) {
			if (c.getPacking().get(i)) {
				Subset subset = c.get(i);
				totalCost += subset.getCost();
			}
		}

		if (totalCost != c.getPacking().getCost())
			throw new CostMismatchException(h.getName());

	}

	public static void checkSubsetIndex(Heuristic h) throws IndexMismatchException {
		Collection c = h.getCollection();
		for (int i = 0; i < c.getCollection().size(); i++) {
			if (c.getCollection().get(i).getIndex() != i)
				throw new IndexMismatchException(h.getName());
		}

	}

	private static List<Subset> getMostRelevantItens(Collection c, Packing curS, boolean value) {
		List<Subset> list = new ArrayList<Subset>();

		for (int i = curS.size() - 1; i >= 0; i--) {
			if (curS.get(i) == value) {
				list.add(c.get(i));
			}
		}

		return list;
	}

	private static void transformPacking(Collection c, Packing orig, Packing target) {
		for (int i = 0; i < target.size(); i++) {
			if (orig.get(i) != target.get(i))
				orig.set(i, target.get(i), c.get(i).getCost());
		}
	}

	public static void makeFeasible(Collection c, Packing curS) {
		Packing p1 = curS.copy();
		repair2(c, p1);
		saturation2(c, p1);

		Packing p2 = curS.copy();
		repair3(c, p2);
		saturation3(c, p2);

		if (p1.getCost() > p2.getCost())
			transformPacking(c, curS, p1);
		else
			transformPacking(c, curS, p2);
	}

	public static void repair2(Collection c, Packing curS) {
		List<Subset> list = getMostRelevantItens(c, curS, true);
		Collections.sort(list, new SortByCostAndSizeDesc());

		for (int i = 0; i < list.size(); i++) {
			Subset candidate = list.get(i);
			curS.set(candidate.getIndex(), false, candidate.getCost());

			if (HeuristicUtils.isSubsetDisjoint(c, curS))
				break;
		}
	}

	public static void saturation2(Collection c, Packing curS) {
		List<Subset> list = getMostRelevantItens(c, curS, false);
		Collections.sort(list, new SortByCostAndSizeAsc());

		for (int i = 0; i < list.size(); i++) {
			Subset candidate = list.get(i);
			if (HeuristicUtils.isSubsetDisjoint(c, curS, candidate)) {
				curS.set(candidate.getIndex(), true, candidate.getCost());
			}
		}
	}

	public static void repair3(Collection c, Packing curS) {
		for (int i = curS.size() - 1; i >= 0; i--) {

			if (curS.get(i) == true) {
				Subset candidate = c.get(i);
				curS.set(i, false, candidate.getCost());

				if (HeuristicUtils.isSubsetDisjoint(c, curS))
					break;
			}
		}
	}

	public static void saturation3(Collection c, Packing curS) {
		for (int i = 0; i < curS.size(); i++) {
			if (curS.get(i) == false) {
				Subset candidate = c.get(i);
				if (HeuristicUtils.isSubsetDisjoint(c, curS, candidate)) {
					curS.set(i, true, candidate.getCost());
				}
			}
		}
	}
}
