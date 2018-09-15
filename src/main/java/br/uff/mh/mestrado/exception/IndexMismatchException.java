package br.uff.mh.mestrado.exception;

public class IndexMismatchException extends Exception {

	private static final long serialVersionUID = 1L;

	public IndexMismatchException(String name) {
		super("Index of subset is not matching. Heuristic: " + name);
	}

}
