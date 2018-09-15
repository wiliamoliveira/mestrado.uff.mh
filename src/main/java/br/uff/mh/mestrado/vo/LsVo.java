package br.uff.mh.mestrado.vo;

import javafx.beans.property.SimpleIntegerProperty;

public class LsVo {

	private SimpleIntegerProperty add;
	private SimpleIntegerProperty del;

	public LsVo(Integer del, Integer add) {
		this.add = new SimpleIntegerProperty(add);
		this.del = new SimpleIntegerProperty(del);
	}

	public Integer getAdd() {
		return add.getValue();
	}

	public void setAdd(Integer add) {
		this.add.setValue(add);
		;
	}

	public Integer getDel() {
		return del.getValue();
	}

	public void setDel(Integer del) {
		this.del.setValue(del);
		;
	}
}
