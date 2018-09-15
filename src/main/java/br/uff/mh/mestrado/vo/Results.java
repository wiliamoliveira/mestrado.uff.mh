package br.uff.mh.mestrado.vo;

import br.uff.mh.mestrado.utils.DateUtils;

public class Results {

	private int currentLoop;
	private String taskName;

	private int maxCost;
	private int minCost;

	private long loopTimeStart;
	private double loopTimeEnd;

	private long jobTimeStart;
	private double minJobTime;
	private double maxJobTime;

	public Results(String taskName) {
		this.taskName = taskName;
		this.minCost = Integer.MAX_VALUE;
		this.maxCost = 0;
		this.minJobTime = Double.MAX_VALUE;
		this.maxJobTime = 0;
	}

	public void startAll() {
		loopTimeStart = System.nanoTime();
	}

	public void endAll() {
		loopTimeEnd = DateUtils.getElapsedTime(loopTimeStart);
	}

	public void startJob() {
		jobTimeStart = System.nanoTime();
	}

	public void endJob() {
		double end = DateUtils.getElapsedTime(jobTimeStart);
		endAll();

		if (end < minJobTime)
			minJobTime = end;

		if (end > maxJobTime)
			maxJobTime = end;
	}

	public void updatePackingCost(int cost) {
		if (cost > maxCost)
			maxCost = cost;

		if (cost < minCost)
			minCost = cost;
	}
	
	public void clear() {
		this.minCost = Integer.MAX_VALUE;
		this.maxCost = 0;
		this.minJobTime = Double.MAX_VALUE;
		this.maxJobTime = 0;
	}

	public String getLoopTotalTime() {
		return DateUtils.formatElapsedTimeString(loopTimeEnd);
	}

	public long getElapsedTimeTillNow() {
		return DateUtils.getElapsedTimeInSecs(loopTimeStart);
	}

	public String getMinJobTime() {
		return DateUtils.formatElapsedTimeString(minJobTime);
	}

	public String getMaxJobTime() {
		return DateUtils.formatElapsedTimeString(maxJobTime);
	}

	public int getMaxCost() {
		return maxCost;
	}

	public int getMinCost() {
		return minCost;
	}

	public int getCurrentLoop() {
		return currentLoop;
	}

	public void setCurrentLoop(int currentLoop) {
		this.currentLoop = currentLoop;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public long getLoopTimeStart() {
		return loopTimeStart;
	}
}
