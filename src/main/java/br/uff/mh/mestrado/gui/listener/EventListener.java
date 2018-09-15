package br.uff.mh.mestrado.gui.listener;

public interface EventListener<T> extends Runnable {

	public void update(T t);
}
