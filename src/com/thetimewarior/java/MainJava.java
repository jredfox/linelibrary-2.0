package com.thetimewarior.java;

import java.io.File;

import com.EvilNotch.lib.util.line.Line;
import com.EvilNotch.lib.util.line.LineArray;
import com.EvilNotch.lib.util.line.config.ConfigLine;

@SuppressWarnings("unused")
public class MainJava {
	
	public static void main(String[] args)
	{
		Line l = new Line("Zombie");
		/*ConfigLine cfg = new ConfigLine(new File("C:/Users/jredfox/Desktop/minecraft.txt"));
		cfg.header = "DungeonTweaks";
		sw.start();
		cfg.loadConfig();
		cfg.alphabitize();
//		System.out.println(cfg);
		cfg.saveConfig(false);*/
		System.out.println(l);
		LineArray line = new LineArray("%modid@block% $1$ = %this is a string%",'@','%',"$$");
		
	}

}
