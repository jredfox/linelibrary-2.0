package com.EvilNotch.lib.util.Line;

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
	public Object[] getHeads() 
	{
		return new Object[]{this.key};
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
