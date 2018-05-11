package com.sim.utils;


import com.sim.entity.Choice;
import com.sim.entity.service.Operation;

public class RunUtil {
	public static final Choice choice=Choice.ENERGYBASED;
	public static void run() {
		WriteUtil.initialize();
		CountUtil.initialize();
		MessageUtil.initialize();
		int times=100;
		for (int i = 0; i < times; i++) {
			IDUtil.initialize();
			MessageUtil.initialize();
			// nodesNum, msgNum, masterNum,msgCost
			MessageUtil.setConfiguration(100, 50, 5, 10);
			
			Operation op=new Operation();
			//isAlive, nodeEnergy, masterEnergy, rowsNum
			
			op.setConfiguration(true, 500, 1500, 5);
			
			op.setPrintTitleVisible(false);
	
			op.run(choice);		
		}
		WriteUtil.writeLogB("Total failed times:" + CountUtil.getFailedTimes());
		WriteUtil.clear();
	}
}
