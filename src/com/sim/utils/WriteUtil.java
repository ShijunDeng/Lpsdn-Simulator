package com.sim.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteUtil {
	private static BufferedWriter buffA = null;
	private static BufferedWriter buffB = null;
	private static BufferedWriter buffC = null;

	public static void initialize() {
		try {
			Date date = new Date();
			String path = "result";
			String dataStr = new SimpleDateFormat("yyyy-MM-dd hhmmss").format(date);
			String fileNameA = RunUtil.choice.getName() + "A" + dataStr;
			String fileNameB = RunUtil.choice.getName() + "B" + dataStr;
			String fileNameC = RunUtil.choice.getName() + "C" + dataStr;
			String suffix = ".txt";
			buffA = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File(path + File.separator + fileNameA + suffix))));
			buffB = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File(path + File.separator + fileNameB + suffix))));
			buffC = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File(path + File.separator + fileNameC + suffix))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeLogA(String str) {
		try {
			buffA.write(str);
			buffA.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeLogB(String str) {
		try {
			buffB.write(str);
			buffB.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeLogC(String str) {
		try {
			buffC.write(str);
			buffC.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void clear() {
		try {
			buffA.flush();
			buffA.close();
			buffB.flush();
			buffB.close();
			buffC.flush();
			buffC.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
