package br.uff.mh.mestrado.strategy;

import br.uff.mh.mestrado.vo.Packing;

public interface GeneticCrossingStrategy {
	public Packing crossing(Packing father1, Packing father2);
}
