package com.EvilNotch.lib.util.line.config;

import java.io.File;
import java.util.List;

import com.EvilNotch.lib.util.line.ILine;
import com.EvilNotch.lib.util.line.LangLine;

public class ConfigLang extends ConfigBase{
	
	public ConfigLang(File f) 
	{
		super(f);
		this.commentsEnabled = false;
	}
	
	public ConfigLang(String inputStream,File output)
	{
		super(inputStream,output);
		this.commentsEnabled = false;
	}
	
	public ConfigLang(File f,String header,char commentStart,List<String> comments)
	{
		super(f,header,commentStart,comments);
		this.commentsEnabled = false;
	}
	
	@Override
	public ILine getLineFromString(String str) 
	{
		return new LangLine(str);
	}

}
