package br.uff.mh.mestrado.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.vo.Collection;
import br.uff.mh.mestrado.vo.Subset;

public class SelectByGreaterCostFrequencyRandom implements SelectStrategy {
	private Heuristic heuristic;
	private HashMap<Integer, AtomicInteger> map;

	public SelectByGreaterCostFrequencyRandom(Collection c, Heuristic h) {
		this.heuristic = h;
		this.map = new HashMap<Integer, AtomicInteger>();
		this.setUp(c);
	}

	private void setUp(Collection c) {
		for (int i = 0; i < c.getCollection().size(); i++) {
			Subset s1 = c.get(i);
			map.put(i, new AtomicInteger(1));

			for (int j = i + 1; j < c.getCollection().size(); j++) {
				Subset s2 = c.get(j);
				for (int k = 0; k < s1.size(); k++) {
					if (s1.get(k) && s2.get(k)) {
						map.get(i).incrementAndGet();
					}
				}
			}

			map.get(i).addAndGet(-s1.getCost());
		}
	}

	private List<Subset> selectBetter(List<Subset> set) {
		int min = Integer.MAX_VALUE;
		for (Subset subset : set) {
			int cur = map.get(subset.getIndex()).get();

			if (cur < min)
				min = cur;
		}

		List<Subset> list = new ArrayList<Subset>(set.size());
		for (Subset subset : set) {
			int cur = map.get(subset.getIndex()).get();

			if (cur == min)
				list.add(subset);
		}

		return list;
	}

	public Subset select(List<Subset> subset) {
		List<Subset> candidates = selectBetter(subset);
		int choosen = heuristic.getRandom().nextInt(candidates.size());
		return candidates.get(choosen);
	}
}
