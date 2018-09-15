package br.uff.mh.mestrado.exception;

public class CostMismatchException extends Exception {

	private static final long serialVersionUID = 1L;

	public CostMismatchException(String name) {
		super("Total cost from collection is not matching with packing cost. Heuristic: " + name);
	}
}
