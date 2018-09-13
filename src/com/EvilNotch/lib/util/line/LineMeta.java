package com.EvilNotch.lib.util.line;

import com.EvilNotch.lib.util.JavaUtil;

public class LineMeta extends Line implements ILineMeta{

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
		int currentIndex = this.getId().length();
		this.meta = JavaUtil.parseQuotes(str,currentIndex, "<>");
		currentIndex += this.meta.isEmpty() ? 0 : this.meta.length()+2;
		this.nbt = JavaUtil.parseQuotes(str, currentIndex, "{}");
		if(!this.nbt.isEmpty())
			this.nbt = "{" + this.nbt + "}";
	}
	
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}
	@Override
	public boolean equalsMeta(ILineMeta other)
	{
		String[] meta = this.getMetaData();
		String[] otherMeta = other.getMetaData();
		
		if(meta.length != otherMeta.length)
			return false;
		
		for(int i=0;i<meta.length;i++)
		{
			if(!meta[i].equals(otherMeta[i]))
				return false;
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		String m = "";
		if(!this.meta.isEmpty())
			m += " <" + this.meta + ">";
		if(this.nbt != null)
			m += " " + this.nbt;
		return super.toString() + m;
	}
	
	@Override
	public String[] getMetaData()
	{
		return new String[]{this.meta,this.nbt};
	}

}
