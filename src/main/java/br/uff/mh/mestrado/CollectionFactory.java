package br.uff.mh.mestrado;

import org.apache.commons.math3.random.MersenneTwister;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.vo.Collection;
import br.uff.mh.mestrado.vo.Subset;

public class CollectionFactory {
	public static Collection createCollection(Config config, MersenneTwister random) {
		Collection collection = new Collection(config.getMaxCollectionSize(), config.getMaxSubsetSize(), config.getPathRelinkingCandidateListMaxSize());

		for (int i = 0; i < config.getMaxCollectionSize(); i++) {
			collection.add(createSubset(config, random));
		}

		collection.sort();

		return collection;
	}

	private static Subset createSubset(Config config, MersenneTwister random) {
		int cost = random.nextInt(config.getMaxCost()) + 1;
		int size = config.getMaxSubsetSize();
		// int size = random.nextInt(config.getMaxSubsetSize());
		//
		// while (size == 0) {
		// size = random.nextInt(config.getMaxSubsetSize());
		// }

		Subset subset = new Subset(cost, size);
		int maxIt = (int) (size * config.getDistribution());

		if (maxIt < 1)
			maxIt = 1;

		for (int i = 0; i < maxIt; i++) {
			subset.set(random.nextInt(size), true);
		}

		return subset;
	}
}
