package com.thetimewarior.java;

import com.EvilNotch.lib.util.line.LineArray;

public class MainJava {
	
	public static void main(String[] args)
	{
		LineArray line = new LineArray("modid:block <int> {nbt} = (20)",':');
		System.out.println(line);
	}

}
