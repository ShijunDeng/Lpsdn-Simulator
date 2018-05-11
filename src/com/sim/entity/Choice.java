package com.sim.entity;

public enum Choice {
	WITHLOOP("Withloop"), WITHOUTLOOP("Withoutloop"), ENERGYBASED("EnergyBased");
	private Choice(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
