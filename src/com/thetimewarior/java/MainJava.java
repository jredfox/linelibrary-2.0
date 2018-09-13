package com.thetimewarior.java;

import com.EvilNotch.lib.util.Line.Line;
import com.EvilNotch.lib.util.Line.LineMeta;


public class MainJava {
	
	public static void main(String[] args)
	{
		LineMeta line = new LineMeta("modid:block <int> {nbt} = 20",':');
		System.out.println(line);
	}

}
