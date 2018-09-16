package com.EvilNotch.lib.util.line.config;

import java.io.File;

import com.EvilNotch.lib.util.line.ILine;
import com.EvilNotch.lib.util.line.LangLine;

public class ConfigLang extends ConfigBase{

	public ConfigLang(File f) 
	{
		super(f);
		this.commentsEnabled = false;
		this.headerComments.clear();//to ensure another mod isn't stupid trying to add comments to a file which doesn't allow any comments not even from the coder
	}
	
	@Override
	public ILine getLineFromString(String str) 
	{
		return new LangLine(str);
	}

}
