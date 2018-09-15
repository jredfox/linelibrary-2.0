package com.thetimewarior.java;

import java.io.File;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.ILineHeadArray;
import com.EvilNotch.lib.util.line.config.ConfigLine;

@SuppressWarnings("unused")
public class MainJava {
	
	public static void main(String[] args)
	{
		long stamp = System.currentTimeMillis();
		ConfigLine cfg = new ConfigLine(new File("C:/Users/jredfox/Desktop/minecraft.txt"));
		cfg.header = "DungeonTweaks";
		cfg.loadConfig();
		cfg.alphabitize();
		ILineHeadArray line = (ILineHeadArray) cfg.lines.get(0);
		line.setObject(100, 0);
		System.out.println(cfg);
		cfg.saveConfig(false);
		JavaUtil.printTime(stamp, "Done:");
	}

}
