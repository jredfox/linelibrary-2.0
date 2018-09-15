package com.thetimewarior.java;

import java.io.File;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.LineArray;
import com.EvilNotch.lib.util.line.config.ConfigLine;

@SuppressWarnings("unused")
public class MainJava {
	
	public static void main(String[] args)
	{
		long stamp = System.currentTimeMillis();
		ConfigLine cfg = new ConfigLine(new File("C:/Users/jredfox/Desktop/minecraft.txt"));
		cfg.header = "DungeonTweaks";
		cfg.origin = cfg.toFileLines();
//		cfg.alphabitize();
		cfg.saveConfig(false);
//		System.out.print(cfg);
		JavaUtil.printTime(stamp, "Done:");
	}

}
