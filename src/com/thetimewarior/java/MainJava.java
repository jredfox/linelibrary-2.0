package com.thetimewarior.java;

import java.io.File;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.config.ConfigLine;

public class MainJava {
	
	public static void main(String[] args)
	{
		long stamp = System.currentTimeMillis();
		ConfigLine cfg = new ConfigLine(new File("C:/Users/jredfox/Desktop/minecraft.txt"));
		System.out.println(cfg);
		JavaUtil.printTime(stamp, "Done:");
	}

}
