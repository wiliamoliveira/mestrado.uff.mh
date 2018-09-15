package br.uff.mh.mestrado.strategy;

import java.util.List;

import br.uff.mh.mestrado.vo.Subset;

public interface SelectStrategy {
	public Subset select(List<Subset> subset);
}
