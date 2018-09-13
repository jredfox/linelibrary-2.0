package com.EvilNotch.lib.util.line;

import java.util.List;

import com.EvilNotch.lib.util.JavaUtil;

public class LangLine implements ILineHead{
	
	public String key;
	public String value;
	
	public LangLine(String str)
	{
		String[] parts = str.split("=");
		this.key = parts[0].trim();
		this.value = parts[1].trim();
	}

	@Override
	public String getId() 
	{
		return this.key;
	}

	@Override
	public String getComparible() 
	{
		return this.key;
	}

	@Override
	public List<Object> getHeads() 
	{
		return JavaUtil.asList(this.value);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof LangLine))
			return false;
		LangLine line = (LangLine)obj;
		return this.key.equals(line.key) && this.value.equals(line.value);
	}
	
	@Override
	public int hashCode()
	{
		return this.key.hashCode();
	}
	
	@Override
	public String toString()
	{
		return this.key + "=" + this.value;
	}

}
