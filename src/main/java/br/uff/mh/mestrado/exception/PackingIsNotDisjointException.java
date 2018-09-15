package br.uff.mh.mestrado.exception;

public class PackingIsNotDisjointException extends Exception {

	private static final long serialVersionUID = 1L;

	public PackingIsNotDisjointException(String name) {
		super("Packing is not disjont. Heuristic: " + name);
	}

}
