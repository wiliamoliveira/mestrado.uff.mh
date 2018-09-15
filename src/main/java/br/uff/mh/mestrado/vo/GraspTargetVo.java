package br.uff.mh.mestrado.vo;

public class GraspTargetVo {

	private int cost;
	private long time;
	private int loop;

	public GraspTargetVo(int cost, long time, int loop) {
		this.cost = cost;
		this.time = time;
		this.loop = loop;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getLoop() {
		return loop;
	}

	public void setLoop(int loop) {
		this.loop = loop;
	}

	@Override
	public String toString() {
		return "GraspTargetVo [cost=" + cost + ", time=" + time + ", loop=" + loop + "]";
	}

	
}
