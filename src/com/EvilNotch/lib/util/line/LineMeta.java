package com.EvilNotch.lib.util.line;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.util.LineUtil;

public class LineMeta extends Line implements ILineMeta{

	/**
	 * standard metadata for everything that is not an int
	 */
	public String meta = "";
	/**
	 * primitive version when it is a number
	 */
	public Number metaData = null;
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
	public char[] metaBrackets;
	
	public LineMeta(String str)
	{
		this(str,LineUtil.sep,LineUtil.quote,LineUtil.metaBrackets,LineUtil.lineInvalid);
	}
	/**
	 * Equivalent to LineItemstack except it's a faster parser
	 */
	public LineMeta(String str, char sep,char quote,char[] metaBrackets,String invalid) 
	{
		super(str,sep,quote,invalid);
		this.metaBrackets = metaBrackets;
		int currentIndex = this.getId().length();
		
		this.meta = LineUtil.getFirstBrackets(currentIndex, str,this.quote, this.metaBrackets[0], this.metaBrackets[1]);
		if(this.meta == null)
			this.meta = "";
		
		currentIndex += this.meta.isEmpty() ? 0 : this.meta.length()+2;
		//calculate the current index before parsing string meta first so it goes straight to the nbt
		if(this.meta.startsWith("" + this.quote))
		{
			this.metaQuote = true;
			this.meta = this.meta.substring(1, this.meta.length()-1);
		}
		else if(JavaUtil.isStringNum(this.meta))
		{
			this.metaData  = (Number)LineUtil.parseWeight(this.meta,this.quote);
			this.metaDataId = JavaUtil.getNumId(this.meta);
		}
		
		this.nbt = LineUtil.getBrackets(currentIndex, str,this.quote, '{', '}');
	}
	
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}
	@Override
	public boolean equalsMeta(ILine other)
	{
		if(!(other instanceof ILineMeta))
			return LineUtil.isNullMeta(this);
		
		String[] meta = this.getMetaData();
		String[] otherMeta = ((ILineMeta)other).getMetaData();
		
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
			m += " " + this.metaBrackets[0] + me + "" + this.metaBrackets[1];
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
