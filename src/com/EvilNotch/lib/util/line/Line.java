package com.EvilNotch.lib.util.line;

import java.util.ArrayList;
import java.util.List;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.comment.IComment;
import com.EvilNotch.lib.util.line.comment.ICommentAttatch;
import com.EvilNotch.lib.util.line.comment.ICommentStorage;
import com.EvilNotch.lib.util.line.util.LineUtil;

import net.minecraft.util.ResourceLocation;

public class Line extends LineComment implements ILineSeperation{
	
	public String modid;
	public String name;
	
	public char seperator;
	public char quote;
	public boolean hasQuote;
	
	public Line(String str)
	{
		this(str,LineUtil.sep,LineUtil.quote);
	}
	
	public Line(String str,char sep,char q)
	{
		this.seperator = sep;
		this.quote = q;
		
		if(str.trim().startsWith("" + this.quote))
		{
			str = JavaUtil.parseQuotes(str, 0, "" + this.quote);
			this.hasQuote = true;
		}
		
		String[] parts = JavaUtil.splitFirst(str, sep);
		if(this.hasQuote)
		{
			this.modid = parts[0];
			if(parts.length == 2)
				this.name = parts[1];
		}
		else
		{
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
	}

	@Override
	public String getId()
	{
		String id = this.modid;
		if(this.name != null)
		{
			id += this.seperator + this.name;
		}
		return id;
	}
	
	@Override
	public char getSeprator() {
		return this.seperator;
	}
	@Override
	public char getQuote(){
		return this.quote;
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
		return this.toString(false);
	}
	@Override
	public String getComparible() 
	{
		return this.toString(true);
	}
	/**
	 * allow to toString() method to be based upon whether or not it should have quotes and stuff
	 */
	public String toString(boolean comparible) 
	{
		String str = this.getId();
		if(this.hasQuote && !comparible)
			str = "" + this.quote + str + this.quote;
		return str;
	}
	
	@Override
	public ResourceLocation getResourceLocation()
	{
		if(this.name == null)
			return new ResourceLocation("minecraft:" + this.modid);
		return new ResourceLocation(this.getId());
	}

}
