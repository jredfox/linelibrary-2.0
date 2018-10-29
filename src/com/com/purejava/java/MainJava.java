package com.purejava.java;
import java.io.File;

import com.evilnotch.lib.util.JavaUtil;
import com.evilnotch.lib.util.line.Line;
import com.evilnotch.lib.util.line.comment.Comment;
import com.evilnotch.lib.util.line.config.ConfigBase;
import com.evilnotch.lib.util.line.config.ConfigLine;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

public class MainJava {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		ConfigBase base = new ConfigLine(new File("C:/Users/jredfox/Desktop/test.txt"),JavaUtil.<String>asArray("header one"));
		base.header = "headerTest";
//		System.out.println("" + base.headerComments);
		base.loadConfig();
//		System.out.println("done loading config:" + base.headerComments);
		base.addLine(new Line("modid:block"));
		base.saveConfig();
		base.addLineComment(new Line("modid:block"),"bbb", true);
//		base.saveConfig();
	}

	private static NBTTagCompound getNBTFromString(String string) 
	{
		try {
			return JsonToNBT.getTagFromJson(string);
		} catch (NBTException e) {
			e.printStackTrace();
		}
		return null;
	}

}
