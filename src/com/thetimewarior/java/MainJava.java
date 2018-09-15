package com.thetimewarior.java;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.LineMeta;

@SuppressWarnings("unused")
public class MainJava {
	
	public static void main(String[] args)
	{
		long stamp = System.currentTimeMillis();
		/*ConfigLine cfg = new ConfigLine(new File("C:/Users/jredfox/Desktop/minecraft.txt"));
		cfg.header = "DungeonTweaks";
		cfg.loadConfig();
		cfg.alphabitize();
		System.out.println(cfg);
		cfg.saveConfig(false);*/
		LineMeta line = new LineMeta("%modid@block% $1L$",'@','%',"$$");
		System.out.println(line);
		
		JavaUtil.printTime(stamp, "Done:");
	}

}
