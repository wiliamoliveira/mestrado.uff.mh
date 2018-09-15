package br.uff.mh.mestrado.vo;

public class ResultsVo {
	private String taskName;
	private String minCost;
	private String maxCost;
	private String lsCost;
	private String prCost;
	private String graspMinCost;
	private String graspMaxCost;
	private String maxTime;
	private String totalTime;

	public ResultsVo() {

	}

	public ResultsVo(String taskName, String minCost, String maxCost, String lsCost, String prCost, String graspMinCost, String graspMaxCost, String maxTime,
			String totalTime) {
		this.taskName = taskName;
		this.minCost = minCost;
		this.maxCost = maxCost;
		this.lsCost = lsCost;
		this.prCost = prCost;
		this.graspMinCost = graspMinCost;
		this.graspMaxCost = graspMaxCost;
		this.maxTime = maxTime;
		this.totalTime = totalTime;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getMinCost() {
		return minCost;
	}

	public void setMinCost(String minCost) {
		this.minCost = minCost;
	}

	public String getMaxCost() {
		return maxCost;
	}

	public void setMaxCost(String maxCost) {
		this.maxCost = maxCost;
	}

	public String getLsCost() {
		return lsCost;
	}

	public void setLsCost(String lsCost) {
		this.lsCost = lsCost;
	}

	public String getPrCost() {
		return prCost;
	}

	public void setPrCost(String prCost) {
		this.prCost = prCost;
	}

	public String getGraspMinCost() {
		return graspMinCost;
	}

	public void setGraspMinCost(String graspMinCost) {
		this.graspMinCost = graspMinCost;
	}

	public String getGraspMaxCost() {
		return graspMaxCost;
	}

	public void setGraspMaxCost(String graspMaxCost) {
		this.graspMaxCost = graspMaxCost;
	}

	public String getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	
	
}
