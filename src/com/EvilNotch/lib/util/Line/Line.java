package com.EvilNotch.lib.util.Line;

import com.EvilNotch.lib.util.JavaUtil;

public class Line implements ILineSeperation{
	
	public String modid;
	public String name;
	
	public char seperator;
	
	public Line(String str)
	{
		this(str,':');
	}
	
	public Line(String str,char sep)
	{
		this.seperator = sep;
		
		String[] parts = JavaUtil.splitFirst(str, sep);
		this.modid = parts[0].trim();
		
		if(parts.length == 2)
		{
			StringBuilder builder = new StringBuilder();
			String restOfLine = parts[1].trim();
			for(char c : restOfLine.toCharArray())
			{
				if(c == ' ')
					break;
				builder.append(c);
			}
			this.name = builder.toString();
		}
	}

	@Override
	public String getId()
	{
		String id = this.modid;
		if(this.name != null)
		{
			id += ":" + this.name;
		}
		return id;
	}

	@Override
	public String getComparible() 
	{
		return this.toString();
	}

	@Override
	public String[] getMetaData() {
		return null;
	}

	@Override
	public char getSeprator() {
		return this.seperator;
	}
	
	//regular java stuffs start here
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof ILine))
			return false;
		ILine line = (ILine)obj;
		return this.getId().equals(line.getId());
	}
	/**
	 * speed up the config process
	 */
	@Override
	public int hashCode()
	{
		return this.getId().hashCode();
	}
	@Override
	public String toString()
	{
		return this.getId();
	}

}
