package com.thetimewarior.java;

import java.io.File;

import com.EvilNotch.lib.util.line.Line;
import com.EvilNotch.lib.util.line.LineArray;
import com.EvilNotch.lib.util.line.config.ConfigLine;

@SuppressWarnings("unused")
public class MainJava {
	
	public static void main(String[] args)
	{
		/*ConfigLine cfg = new ConfigLine(new File("C:/Users/jredfox/Desktop/minecraft.txt"));
		cfg.header = "DungeonTweaks";
		cfg.loadConfig();
		cfg.alphabitize();
		System.out.println(cfg);
		cfg.saveConfig(false);*/
		LineArray arr = new LineArray("modid:block = [0,1,2,3,4,[0,1,2,3,[0],4],6,7,8]");
		System.out.println(arr);
//		LineArray line = new LineArray("%modid@block% $1^ = %this is a string%",'@','%',"$^");
	}

}
