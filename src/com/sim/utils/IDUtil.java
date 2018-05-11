package com.sim.utils;

public class IDUtil {
	private static int messageId = 0;
	private static int vNodeId = 0;

	public static int getMessageId() {
		// messageId++;
		return messageId++;
	}

	public static int getvNodeId() {
		// vNodeId++;
		return vNodeId++;
	}

	public static void initialize() {
		messageId = 0;
		vNodeId = 0;
	}
}
