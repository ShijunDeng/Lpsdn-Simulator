package com.sim.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.sim.entity.Choice;
import com.sim.entity.Message;
import com.sim.entity.VNode;

public class MessageUtil {
	public static int nodesNum;
	// the number of messages
	public static int msgNum;
	// the number of master nodes
	public static int masterNum;
	// the consumption of energy of sending a message
	public static int msgCost;
	// the ID of master nodes is stored in masterIDs set
	private static Set<String> masterIDs;

	public static void addMaster(String masterId) {
		masterIDs.add(masterId);
	}

	public static void initialize() {
		masterIDs = null;
		masterIDs = new HashSet<String>();
	}

	public static void setConfiguration(int aNodesNum, int aMsgNum, int aMasterNum, int aMsgCost) {
		// the number of nodes
		MessageUtil.nodesNum = aNodesNum;
		// the number of messages
		MessageUtil.msgNum = aMsgNum;
		// the number of master nodes
		MessageUtil.masterNum = aMasterNum;
		// the consumption of energy of sending a message
		MessageUtil.msgCost = aMsgCost;
		// Producing master nodes
		Random masterIdRandom = new Random(System.currentTimeMillis());
		for (int i = 0; i < MessageUtil.masterNum; i++) {
			// MessageUtil.masterNum:the number of master nodes
			// master ID generated at random
			int masterId = masterIdRandom.nextInt(MessageUtil.nodesNum);
			MessageUtil.addMaster(String.valueOf(masterId));
		}
	}

	public static Set<String> getMasterIDs() {
		return masterIDs;
	}
	
	public static boolean sendMessage(VNode vSender, Message msg, VNode last,Choice choice){
		switch(choice){
		case WITHLOOP:return sendMessageWithLoop(vSender,msg,last);
		case WITHOUTLOOP:return sendMessageWithoutLoop(vSender,msg,last);
		case ENERGYBASED:return sendMessageBasedEnergy(vSender,msg,last);
		}
		return false;
		
	}
	
	public static boolean sendMessageBasedEnergy(VNode vSender, Message msg, VNode last) {
		vSender.setRecvCount(vSender.getRecvCount() + 1);
		if (getMasterIDs().contains(vSender.getID()) == false) {
			vSender.setSentCount(vSender.getSentCount() + 1);
		}
		vSender.setEnergy(vSender.getEnergy() - msg.getCost());
		if (vSender.getEnergy() <= 0) {
			vSender.setAlive(false);
		}
		msg.addVisitedNode(vSender);
		msg.setStep(msg.getStep() + 1);
		if (getMasterIDs().contains(vSender.getID()) == true) {
			String str = " " + vSender.getInfoData() + "		 	" + msg.getInfoData() + "   " + msg.getStep();
			WriteUtil.writeLogA(str);
			msg.getMsgData().setMasterID(vSender.getID());
			return true;
		} else if (vSender.getEnergy() > 0) {
			String str = " " + vSender.getInfoData() + "		 	" + msg.getInfoData() + "";
			WriteUtil.writeLogA(str);
		}
		if (vSender.getNeighbors().size() == 1) {
			String str = "   " + vSender.getInfoData() + " " + msg.getInfoData() + "";
			WriteUtil.writeLogA(str);
			msg.getMsgData().setMasterID("Failed");
			CountUtil.addFailedTimes(1);
			return false;
		}
		VNode newSender = null;
		for (VNode v : vSender.getNeighbors().values()) {
			if ((newSender == null || v.getEnergy() > newSender.getEnergy()) && v.getEnergy() > 0 && v != last) {
				newSender = v;
			}
		}
		if (newSender == null || newSender.getEnergy() < msg.getCost()) {
			String str = "¡ô" + vSender.getInfoData() + "|" + msg.getInfoData() + "";
			WriteUtil.writeLogA(str);
			WriteUtil.writeLogA("Neighbor ID isAlive energy recvCount sentCount");
			for (VNode v : vSender.getNeighbors().values()) {
				WriteUtil.writeLogA("        " + v.getInfoData() + "");
			}
			msg.getMsgData().setMasterID("Failed");
			CountUtil.addFailedTimes(1);
			return false;
		}
		return sendMessageBasedEnergy(newSender, msg, vSender);

	}

	public static boolean sendMessageWithoutLoop(VNode vSender, Message msg, VNode last) {
		vSender.setRecvCount(vSender.getRecvCount() + 1);
		if (getMasterIDs().contains(vSender.getID()) == false) {
			vSender.setSentCount(vSender.getSentCount() + 1);
		}
		vSender.setEnergy(vSender.getEnergy() - msg.getCost());
		if (vSender.getEnergy() <= 0) {
			vSender.setAlive(false);
		}
		msg.addVisitedNode(vSender);
		msg.setStep(msg.getStep() + 1);
		if (getMasterIDs().contains(vSender.getID()) == true) {
			String str = " " + vSender.getInfoData() + "		 	" + msg.getInfoData() + "   " + msg.getStep();
			WriteUtil.writeLogA(str);
			msg.getMsgData().setMasterID(vSender.getID());
			return true;
		} else if (vSender.getEnergy() > 0) {
			String str = " " + vSender.getInfoData() + "		 	" + msg.getInfoData() + "";
			WriteUtil.writeLogA(str);
		}
		if (vSender.getNeighbors().size() == 1) {
			String str = "   " + vSender.getInfoData() + " " + msg.getInfoData() + "";
			WriteUtil.writeLogA(str);
			msg.getMsgData().setMasterID("Failed");
			CountUtil.addFailedTimes(1);
			return false;
		}
		Random idRandom = new Random(System.currentTimeMillis());
		HashSet<String> iDSets = new HashSet<String>();
		if (last != null) {
			iDSets.add(last.getID());
		}
		VNode newSender = vSender.getNeighbors().get(String.valueOf(idRandom.nextInt(nodesNum)));
		while (newSender == null || (last != null && newSender.getID().equals(last.getID()) == true)
				|| newSender.isAlive() == false || newSender.getEnergy() < msg.getCost()
				|| msg.getVisitedNodes().contains(newSender)) {
			if (iDSets.size() == vSender.getNeighbors().size()) {
				String str = "¡ô" + vSender.getInfoData() + "|" + msg.getInfoData() + "";
				WriteUtil.writeLogA(str);
				WriteUtil.writeLogA("Neighbor ID isAlive energy recvCount sentCount");
				for (VNode v : vSender.getNeighbors().values()) {
					WriteUtil.writeLogA("        " + v.getInfoData() + "");
				}
				msg.getMsgData().setMasterID("Failed");
				CountUtil.addFailedTimes(1);
				return false;
			}
			newSender = vSender.getNeighbors().get(String.valueOf(idRandom.nextInt(nodesNum)));
			if (newSender != null) {
				iDSets.add(newSender.getID());
			}
		}
		return sendMessageWithoutLoop(newSender, msg, vSender);

	}

	public static boolean sendMessageWithLoop(VNode vSender, Message msg, VNode last) {
		vSender.setRecvCount(vSender.getRecvCount() + 1);
		if (getMasterIDs().contains(vSender.getID()) == false) {
			vSender.setSentCount(vSender.getSentCount() + 1);
		}
		vSender.setEnergy(vSender.getEnergy() - msg.getCost());
		if (vSender.getEnergy() <= 0) {
			vSender.setAlive(false);
		}
		msg.addVisitedNode(vSender);
		msg.setStep(msg.getStep() + 1);
		if (getMasterIDs().contains(vSender.getID()) == true) {
			String str = " " + vSender.getInfoData() + "		 	" + msg.getInfoData() + "   " + msg.getStep();
			WriteUtil.writeLogA(str);
			msg.getMsgData().setMasterID(vSender.getID());
			return true;
		} else if (vSender.getEnergy() > 0) {
			String str = " " + vSender.getInfoData() + "		 	" + msg.getInfoData() + "";
			WriteUtil.writeLogA(str);
		}
		if (vSender.getNeighbors().size() == 1) {
			String str = "   " + vSender.getInfoData() + " " + msg.getInfoData() + "";
			WriteUtil.writeLogA(str);
			msg.getMsgData().setMasterID("Failed");
			CountUtil.addFailedTimes(1);
			return false;
		}
		Random idRandom = new Random(System.currentTimeMillis());
		HashSet<String> iDSets = new HashSet<String>();
		if (last != null) {
			iDSets.add(last.getID());
		}
		VNode newSender = vSender.getNeighbors().get(String.valueOf(idRandom.nextInt(nodesNum)));
		while (newSender == null || (last != null && newSender.getID().equals(last.getID()) == true)
				|| newSender.isAlive() == false || newSender.getEnergy() < msg.getCost()) {
			if (iDSets.size() == vSender.getNeighbors().size()) {
				String str = "¡ô" + vSender.getInfoData() + "|" + msg.getInfoData() + "";
				WriteUtil.writeLogA(str);
				WriteUtil.writeLogA("Neighbor ID isAlive energy recvCount sentCount");
				for (VNode v : vSender.getNeighbors().values()) {
					WriteUtil.writeLogA("        " + v.getInfoData() + "");
				}
				msg.getMsgData().setMasterID("Failed");
				CountUtil.addFailedTimes(1);
				return false;
			}
			newSender = vSender.getNeighbors().get(String.valueOf(idRandom.nextInt(nodesNum)));
			if (newSender != null) {
				iDSets.add(newSender.getID());
			}
		}
		return sendMessageWithLoop(newSender, msg, vSender);

	}
}
