package com.thetimewarior.java;

import java.io.File;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.ILineHeadArray;
import com.EvilNotch.lib.util.line.LineArray;
import com.EvilNotch.lib.util.line.config.ConfigLine;

@SuppressWarnings("unused")
public class MainJava {
	
	public static void main(String[] args)
	{
		/*long stamp = System.currentTimeMillis();
		ConfigLine cfg = new ConfigLine(new File("C:/Users/jredfox/Desktop/minecraft.txt"));
		cfg.header = "DungeonTweaks";
		cfg.loadConfig();
		cfg.alphabitize();
		System.out.println(cfg);
		cfg.saveConfig(false);
		JavaUtil.printTime(stamp, "Done:");*/
		
		LineArray arr = new LineArray("modid:block = [0,1,2,3,4,[0,1,2,3,[0],4],6,7,8]");
		System.out.println(arr);
	}

}
