package com.EvilNotch.lib.util.line.config;

import java.io.File;

import com.EvilNotch.lib.util.line.ILine;
import com.EvilNotch.lib.util.line.LineDynamicLogic;

public class ConfigDynamicLogic extends ConfigLine{
	
	public ConfigDynamicLogic(File f) 
	{
		super(f);
	}

	@Override
	public ILine getLineFromString(String str) 
	{
		return new LineDynamicLogic(str);
	}

}
