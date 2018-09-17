package com.thetimewarior.java;

import com.EvilNotch.lib.util.line.LineDynamicLogic;

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
		LineDynamicLogic logic = new LineDynamicLogic("\"modid:bl,,ock\" <meta,,Data>,modid:block2 || modid:block");
		System.out.println(logic);
//		LineArray line = new LineArray("%modid@block% $1^ = %this is a string%",'@','%',"$^");
	}

}
