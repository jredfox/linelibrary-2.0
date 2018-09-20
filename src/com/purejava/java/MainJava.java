package com.purejava.java;

import java.util.List;

import com.EvilNotch.lib.util.line.LineArray;
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
		LineArray line = new LineArray("modid:block = (0,1,2,3,(0,1,2l),5)",':','"',LineUtil.metaBrackets,"()".toCharArray(),LineUtil.lineInvalid);
		List<Object> li = (List<Object>) line.heads.get(4);
		System.out.println(line);
	}

}
