package com.EvilNotch.lib.util.line.config;

import java.io.File;

import com.EvilNotch.lib.util.line.ILine;
import com.EvilNotch.lib.util.line.LineMeta;

public class ConfigLineMeta extends ConfigLine{
	
	public ConfigLineMeta(File f) 
	{
		super(f);
	}
	@Override
	public ILine getLineFromString(String str) 
	{
		return new LineMeta(str);
	}

}
