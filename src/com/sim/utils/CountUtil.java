package com.sim.utils;

public class CountUtil {
	public static int failedTimes;

	public static int getFailedTimes() {
		return failedTimes;
	}

	public static void setFailedTimes(int failedTimes) {
		CountUtil.failedTimes = failedTimes;
	}

	public static void addFailedTimes(int times) {
		CountUtil.failedTimes += times;
	}

	public static void initialize() {
		CountUtil.setFailedTimes(0);	
	}
}
