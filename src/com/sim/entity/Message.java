package com.sim.entity;

import java.util.HashSet;
import java.util.Set;

public class Message {
	private String ID;
	private MsgData data;
	private int step;
	private int cost;
	private Set<VNode> visitedNodes;

	public Message() {
		visitedNodes = new HashSet<VNode>();
		data=new MsgData();
	}

	public Message(String iD, int step, int cost) {
		this();
		ID = iD;
		this.step = step;
		this.cost = cost;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public MsgData getMsgData() {
		return data;
	}

	public void setMsgData(MsgData data) {
		this.data = data;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Set<VNode> getVisitedNodes() {
		return visitedNodes;
	}

	public void addVisitedNode(VNode visitedNode) {
		this.visitedNodes.add(visitedNode);
	}
	public String getInfoData() {
		return  this.getID() + "   " + this.getCost() ;
	}

	public String getInfo() {
		return "ID:" + this.getID() + " cost:" + this.getCost() + " step:" + this.getStep();
	}

}
