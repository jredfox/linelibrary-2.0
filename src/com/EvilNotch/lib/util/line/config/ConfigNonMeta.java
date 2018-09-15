package com.EvilNotch.lib.util.line.config;

import java.io.File;

public class ConfigNonMeta extends ConfigLine{

	public ConfigNonMeta(File f) {
		super(f);
	}
	
	@Override
	public boolean checkMetaByDefault()
	{
		return false;
	}

}
