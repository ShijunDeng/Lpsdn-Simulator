package com.sim.entity.service;

import java.util.Random;

import com.sim.entity.Choice;
import com.sim.entity.Message;
import com.sim.entity.VNode;
import com.sim.utils.IDUtil;
import com.sim.utils.MessageUtil;
import com.sim.utils.WriteUtil;

public class Operation {
	private boolean initialState;
	private int initialEnergy;
	private int initMasterEnergy;
	private int initRowsNum;// number of rows
	private boolean printTitle = false;
	private Choice choice;

	public void setConfiguration(boolean state, int nodeEnergy, int masterEnergy, int rowsNum) {
		initialState = state;
		initialEnergy = nodeEnergy;
		initMasterEnergy = masterEnergy;
		initRowsNum = rowsNum;
	}

	public void setPrintTitleVisible(boolean state) {
		printTitle = state;
	}

	public void specialTest() {
		int columnsNum = MessageUtil.nodesNum / initRowsNum;// number of columns
		VNode[] nodes = new VNode[MessageUtil.nodesNum];
		for (int i = 0; i < MessageUtil.nodesNum; i++) { // create the nodes
			nodes[i] = new VNode(String.valueOf(IDUtil.getvNodeId()), initialState, initialEnergy);
		}

		String masterIdStrS = "";
		for (String masterIdStr : MessageUtil.getMasterIDs()) {
			// Producing master nodes
			nodes[Integer.parseInt(masterIdStr)].setEnergy(initMasterEnergy);
			masterIdStrS = masterIdStrS + masterIdStr + " ";
		}
		// configuration
		String configuration = "***************************************************configuration****************************************************";
		WriteUtil.writeLogA(configuration);
		WriteUtil.writeLogA("limitOfID:" + MessageUtil.nodesNum + " msgNum:" + MessageUtil.msgNum + " masterNum:"
				+ MessageUtil.masterNum + " msgCost:" + MessageUtil.msgCost);
		WriteUtil.writeLogA("Master nodes' Id:" + masterIdStrS);

		createGraph(nodes, initRowsNum, columnsNum);

		Random idRandom = new Random(System.currentTimeMillis());
		// generating messages
		Message msgs[] = new Message[MessageUtil.msgNum];
		for (int i = 0; i < MessageUtil.msgNum; i++) {
			// the node which sending a message is at random
			int id = idRandom.nextInt(MessageUtil.nodesNum);
			while (MessageUtil.getMasterIDs().contains(String.valueOf(id))) {
				id = idRandom.nextInt(MessageUtil.nodesNum);
			}
			if (printTitle) {
				String str = "******************************************The process of sending message" + i
						+ "************************************************************************";
				WriteUtil.writeLogA(str);
				WriteUtil.writeLogA(" ID isAlive energy recvCount sentCount ID cost");
			}
			msgs[i] = new Message(String.valueOf(IDUtil.getMessageId()), 0, MessageUtil.msgCost);
			MessageUtil.sendMessage(nodes[id], msgs[i], null,choice);
		}
		if (printTitle) {
			WriteUtil.writeLogB("ID isAlive energy recvCount sentCount");
		}
		for (int i = 0; i < MessageUtil.nodesNum; i++) {
			WriteUtil.writeLogB(nodes[i].getID() + "   " + nodes[i].isAlive() + "    " + nodes[i].getEnergy()
					+ "       " + nodes[i].getRecvCount() + "       " + nodes[i].getSentCount());
		}
		if (printTitle) {
			WriteUtil.writeLogC("ID cost step masterID");
		}
		for (int i = 0; i < MessageUtil.msgNum; i++) {
			WriteUtil.writeLogC(msgs[i].getID() + "   " + msgs[i].getCost() + "    " + msgs[i].getStep() + "   "
					+ msgs[i].getMsgData().getMasterID());
		}

		nodes = null;
		msgs = null;

	}

	public void run(Choice aChoice) {
		this.choice=aChoice;
		specialTest();
	}

	private void createGraph(VNode[] nodes, int m, int n) {
		nodes[0].addNeighbor(nodes[1]);
		nodes[0].addNeighbor(nodes[n]);
		nodes[0].addNeighbor(nodes[n + 1]);

		nodes[n - 1].addNeighbor(nodes[n - 2]);
		nodes[n - 1].addNeighbor(nodes[2 * n - 1]);
		nodes[n - 1].addNeighbor(nodes[2 * n - 2]);

		nodes[(m - 1) * n].addNeighbor(nodes[(m - 2) * n]);
		nodes[(m - 1) * n].addNeighbor(nodes[(m - 2) * n + 1]);
		nodes[(m - 1) * n].addNeighbor(nodes[(m - 1) * n + 1]);

		nodes[m * n - 1].addNeighbor(nodes[(m - 1) * n - 1]);
		nodes[m * n - 1].addNeighbor(nodes[(m - 1) * n - 2]);
		nodes[m * n - 1].addNeighbor(nodes[m * n - 2]);

		for (int i = 1; i < n - 1; i++) {
			nodes[i].addNeighbor(nodes[i - 1]);
			nodes[i].addNeighbor(nodes[i + 1]);
			nodes[i].addNeighbor(nodes[i + n - 1]);
			nodes[i].addNeighbor(nodes[i + n]);
			nodes[i].addNeighbor(nodes[i + n + 1]);
		}

		for (int i = (m - 1) * n + 1; i < m * n - 1; i++) {
			nodes[i].addNeighbor(nodes[i - 1]);
			nodes[i].addNeighbor(nodes[i + 1]);
			nodes[i].addNeighbor(nodes[i - n - 1]);
			nodes[i].addNeighbor(nodes[i - n]);
			nodes[i].addNeighbor(nodes[i - n + 1]);
		}

		for (int j = n; j < (m - 1) * n; j += n) {
			nodes[j].addNeighbor(nodes[j - n]);
			nodes[j].addNeighbor(nodes[j - n + 1]);
			nodes[j].addNeighbor(nodes[j + 1]);
			nodes[j].addNeighbor(nodes[j + n]);
			nodes[j].addNeighbor(nodes[j + n + 1]);
		}
		for (int j = n * 2 - 1; j < m * n - 1; j += n) {
			nodes[j].addNeighbor(nodes[j - n]);
			nodes[j].addNeighbor(nodes[j - n - 1]);
			nodes[j].addNeighbor(nodes[j - 1]);
			nodes[j].addNeighbor(nodes[j + n]);
			nodes[j].addNeighbor(nodes[j + n - 1]);
		}

		for (int i = 1; i < m - 1; i++) {
			for (int j = 1; j < n - 1; j++) {
				nodes[i * n + j].addNeighbor(nodes[i * n + j - 1]);
				nodes[i * n + j].addNeighbor(nodes[i * n + j + 1]);
				nodes[i * n + j].addNeighbor(nodes[i * n + j - n]);
				nodes[i * n + j].addNeighbor(nodes[i * n + j + n]);
				nodes[i * n + j].addNeighbor(nodes[i * n + j - n - 1]);
				nodes[i * n + j].addNeighbor(nodes[i * n + j - n + 1]);
				nodes[i * n + j].addNeighbor(nodes[i * n + j + n - 1]);
				nodes[i * n + j].addNeighbor(nodes[i * n + j + n + 1]);
			}
		}
	}

}
