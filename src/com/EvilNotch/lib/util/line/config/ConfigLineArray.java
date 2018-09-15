package com.EvilNotch.lib.util.line.config;

import java.io.File;

import com.EvilNotch.lib.util.line.ILine;
import com.EvilNotch.lib.util.line.LineArray;

public class ConfigLineArray extends ConfigLine{

	public ConfigLineArray(File f) {
		super(f);
	}
	@Override
	public ILine getLineFromString(String str) 
	{
		return new LineArray(str);
	}

}
