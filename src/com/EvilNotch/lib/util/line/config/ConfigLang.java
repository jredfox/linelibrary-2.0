package com.EvilNotch.lib.util.line.config;

import java.io.File;

import com.EvilNotch.lib.util.line.ILine;
import com.EvilNotch.lib.util.line.LangLine;

public class ConfigLang extends ConfigLine{

	public ConfigLang(File f) 
	{
		super(f);
	}
	
	@Override
	public ILine getLineFromString(String str) 
	{
		return new LangLine(str);
	}

}
