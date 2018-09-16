package com.EvilNotch.lib.util.line;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.util.LineUtil;

public class LineMeta extends Line implements ILineMeta{

	/**
	 * standard metadata for everything that is not an int
	 */
	public String meta = null;
	/**
	 * primitive version when it is a number
	 */
	public Object metaData;
	/**
	 * save the id of the primitive/string type if any
	 */
	public char metaDataId = ' ';
	/**
	 * if true this will tell it to put "" between the meta string
	 */
	public boolean metaQuote;
	/**
	 * this in mc is NBTTagCompound in pure java it's either string or json you decide
	 */
	public String nbt = null;
	/**
	 * this can be customized besides just "<",">" as brackets
	 */
	public String metaBrackets = "<>";
	
	public LineMeta(String str)
	{
		this(str,':','"',"<>");
	}
	/**
	 * Equivalent to LineItemstack except it's a faster parser
	 */
	public LineMeta(String str, char sep,char quote,String metaBrackets) 
	{
		super(str,sep,quote);
		this.metaBrackets = metaBrackets;
		int currentIndex = this.getId().length();
		this.meta = JavaUtil.parseQuotes(str,currentIndex, this.metaBrackets);
		currentIndex += this.meta.isEmpty() ? 0 : this.meta.length()+2;
		//calculate the current index before parsing string meta first so it goes straight to the nbt
		if(this.meta.startsWith("" + this.quote))
		{
			this.metaQuote = true;
			this.meta = this.meta.substring(1, this.meta.length()-1);
		}
		else if(JavaUtil.isStringNum(this.meta))
		{
			LineArray.Entry arr = LineUtil.parseWeight(this.meta,this.quote);
			this.metaData = arr.obj;
			this.metaDataId = arr.id;
		}
		
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
	
	public int getMetaInt(){
		return (int)this.metaData;
	}
	public long getMetaLong(){
		return (long)this.metaData;
	}
	public double getMetaDouble(){
		return (double)this.metaData;
	}
	
	@Override
	public String toString(boolean comparible)
	{
		String m = "";
		if(!this.meta.isEmpty())
		{
			String me = this.meta;
			if(this.metaQuote && !comparible)
				me = "\"" + me + (this.metaDataId != ' ' ? this.metaDataId : "") + "\"";
			m += " " + this.metaBrackets.charAt(0) + me + "" + this.metaBrackets.charAt(1);
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
