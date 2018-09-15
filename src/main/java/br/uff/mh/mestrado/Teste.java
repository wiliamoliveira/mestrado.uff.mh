package br.uff.mh.mestrado;

import org.apache.commons.math3.random.MersenneTwister;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.vo.Collection;

public class Teste {

	public static void main(String[] args) {
		Config config = new Config();
		config.setMaxCollectionSize(40);
		config.setMaxSubsetSize(20);
		config.setMaxCost(100);
		Collection c = CollectionFactory.createCollection(config, new MersenneTwister(1));

		System.out.println(c);
		
//		MyPacking mp = new MyPacking(10);
//		mp.set(3, true, 10);
//		mp.set(6,true, 5);
//		
//		MyPacking copy = mp.copy();
//		
//		System.out.println(mp);
//		System.out.println(copy);
//		System.out.println(copy==mp);
//		System.out.println(copy==copy);

	}

}
