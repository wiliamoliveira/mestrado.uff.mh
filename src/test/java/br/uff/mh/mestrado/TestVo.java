package br.uff.mh.mestrado;

import org.junit.Assert;
import org.junit.Test;

import br.uff.mh.mestrado.vo.Packing;

public class TestVo {
	
	@Test
	public void testPacking() {
		Packing mp = new Packing(10);
		mp.set(3, true, 10);
		mp.set(6,true, 5);
		mp.set(7,true, 20);
		mp.set(3, false, 10);
		
		Packing copy = mp.copy();
		
		Assert.assertEquals(mp.get(3), false);
		Assert.assertEquals(mp.get(6), true);
		Assert.assertEquals(mp.get(7), true);
		Assert.assertEquals(mp.getCost(), 25);
		Assert.assertEquals(mp.toString(), copy.toString());
		Assert.assertEquals(mp.getCost(), mp.getCost());
		Assert.assertEquals(mp, copy);
	}

}
