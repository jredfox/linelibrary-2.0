package com.purejava.java;

import com.EvilNotch.lib.util.line.LineDynamicLogic;
import com.EvilNotch.lib.util.line.util.LineUtil;

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
//		LineDynamicLogic logic = new LineDynamicLogic("\"modid:block\" <metaData>,modid:block2 || modid:block");
//		System.out.println(logic);
//		LineArray line = new LineArray("%modid@block% $1^ = %this is a string%",'@','%',"$^");
		
		System.out.println(LineUtil.getBrackets(0, "{powered:1,SpawnData:{},name:\"aa{}{{{{{}aa\"}", '"', '{', '}'));
	}

}
