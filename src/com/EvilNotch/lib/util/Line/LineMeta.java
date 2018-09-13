package com.EvilNotch.lib.util.Line;

import com.EvilNotch.lib.util.JavaUtil;

public class LineMeta extends Line{

	public String meta = null;
	public String nbt = null;
	
	public LineMeta(String str)
	{
		this(str,':');
	}
	/**
	 * Equivalent to LineItemstack except it's a faster parser
	 */
	public LineMeta(String str, char sep) 
	{
		super(str, sep);
		int currentIndex = this.modid.length()-1 + this.name.length();
		this.meta = JavaUtil.parseQuotes(str,currentIndex, "<>");
		currentIndex += this.meta.isEmpty() ? 0 : this.meta.length()-1;
		this.nbt = JavaUtil.parseQuotes(str, currentIndex, "{}");
		if(!this.nbt.isEmpty())
			this.nbt = "{" + this.nbt + "}";
		else
			this.nbt = null;
	}
	
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}
	
	@Override
	public String toString()
	{
		String m = "";
		if(!this.meta.isEmpty())
			m += " " + this.meta;
		if(this.nbt != null)
			m += " " + this.nbt;
		return super.toString() + m;
	}
	
	@Override
	public String[] getMetaData()
	{
		return new String[]{this.meta,nbt};
	}

}
