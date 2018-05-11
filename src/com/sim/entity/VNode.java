package com.sim.entity;

import java.util.HashMap;

public class VNode {
	private String ID;
	private boolean isAlive;
	private int energy;
	private int recvCount;
	private int sentCount;
	private HashMap<String, VNode> neighbors;

	public VNode() {
		this.recvCount = 0;
		this.sentCount = 0;
		this.neighbors = new HashMap<String, VNode>();
	}

	public VNode(String iD, boolean isAlive, int energy) {
		this();
		ID = iD;
		this.isAlive = isAlive;
		this.energy = energy;

	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getRecvCount() {
		return recvCount;
	}

	public void setRecvCount(int recvCount) {
		this.recvCount = recvCount;
	}

	public int getSentCount() {
		return sentCount;
	}

	public void setSentCount(int sentCount) {
		this.sentCount = sentCount;
	}

	public HashMap<String, VNode> getNeighbors() {
		return neighbors;
	}

	public void addNeighbor(VNode neighbor) {
		this.neighbors.put(neighbor.getID(), neighbor);
	}

	public String getInfoData() {
		return this.getID() + "   " + this.isAlive + "    " + this.getEnergy() + "     " + this.getRecvCount()
				+ "   		 " + this.getSentCount();
	}

	public String getInfo() {
		return "ID:" + this.getID() + " isAlive:" + this.isAlive + " energy:" + this.getEnergy() + " recvCount:"
				+ this.getRecvCount() + " sentCount:" + this.getSentCount();
	}
}
