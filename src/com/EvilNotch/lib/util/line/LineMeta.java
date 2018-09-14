package com.EvilNotch.lib.util.line;

import com.EvilNotch.lib.util.JavaUtil;

public class LineMeta extends Line implements ILineMeta{

	public String meta = null;
	public int metaInt;
	public boolean metaQuote;
	public String nbt = null;
	
	public LineMeta(String str)
	{
		this(str,':','"');
	}
	/**
	 * Equivalent to LineItemstack except it's a faster parser
	 */
	public LineMeta(String str, char sep,char quote) 
	{
		super(str,sep,quote);
		int currentIndex = this.getId().length();
		this.meta = JavaUtil.parseQuotes(str,currentIndex, "<>");
		currentIndex += this.meta.isEmpty() ? 0 : this.meta.length()+2;
		//calculate the current index before parsing string meta first so it goes straight to the nbt
		if(this.meta.startsWith("" + this.quote))
		{
			this.metaQuote = true;
			this.meta = this.meta.substring(1, this.meta.length()-1);
		}
		else if(JavaUtil.isStringNum(this.meta))
			this.metaInt = Integer.parseInt(this.meta);
		
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
	public String toString(boolean comparible)
	{
		String m = "";
		if(!this.meta.isEmpty())
		{
			String me = this.meta;
			if(this.metaQuote && !comparible)
				me = "\"" + me + "\"";
			m += " <" + me + ">";
		}
		if(this.nbt != null && !this.nbt.isEmpty())
			m += " " + this.nbt;
		return super.toString(comparible) + m;
	}
	
	@Override
	public String[] getMetaData()
	{
		return new String[]{this.meta,this.nbt};
	}

}
