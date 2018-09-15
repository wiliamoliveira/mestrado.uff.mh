package br.uff.mh.mestrado.heuristic;

public enum HeuristicType {
	HCMCRAlfa("HCMCRAlfa"), HCMC("HCMC"), HCPC("HCPC"), HCMCR("HCMCR"), HCMCFR("HCMCFR"), HCTNV("HCTNV"), LS("LS"), PRFor("PR-For"), PRBack(
			"PR-Back"), PRForAndBack("PR-For-and-back"), GRASP("GRASP"), GRASPTarget("GRASP-Target"), Genetic("Genetic");

	private String name;

	private HeuristicType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
