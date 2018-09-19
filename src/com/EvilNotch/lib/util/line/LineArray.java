package com.EvilNotch.lib.util.line;

import java.util.ArrayList;
import java.util.List;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.util.LineUtil;

public class LineArray extends LineMeta implements ILineHeadArray{

	public List<Object> heads = new ArrayList<Object>();
	public char lbracket = ' ';
	public char rbracket = ' ';
	
	public LineArray(String str)
	{
		this(str,LineUtil.sep,LineUtil.quote,LineUtil.metaBrackets,LineUtil.arrBrackets);
	}
	
	public LineArray(String str, char sep,char q,char[] metaBrackets,char[] brackets) 
	{
		super(str, sep,q,metaBrackets);
		if(str.contains("="))
		{
			str = JavaUtil.splitFirst(str,'=')[1].trim();
			
			this.lbracket = brackets[0];
			this.rbracket = brackets[1];
			if(str.startsWith("" + brackets[0]))
			{
				str = str.substring(1, str.length()-1);
			}
			
			String[] toParse = LineUtil.selectString(str, ',',q,this.lbracket,this.rbracket);
			parseHead(toParse,this.heads);
		}
	}
	@Override
	public List<Object> getHeads() 
	{
		return heads;
	}
	/**
	 * return actual objects when applicable
	 */
	@Override
	public Object getHead(int index) 
	{
		Object obj = this.heads.get(index);
		if(obj instanceof Entry)
			obj = ((Entry)obj).obj;
		return obj;
	}
	/**
	 * set the lines value
	 */
	@Override
	public void setHead(Object obj,int index) 
	{
		if(!(obj instanceof Entry))
		{
			if(obj instanceof String)
				obj = new Entry(obj,'"');
			else if(obj instanceof Number && !(obj instanceof Integer) && !(obj instanceof Double))
				obj = new Entry(obj,JavaUtil.getNumId((Number)obj) );
			else
				obj = new Entry(obj);
		}
		this.heads.set(index, obj);
	}
	@Override
	public int size()
	{
		return this.heads.size();
	}
	@Override
	public String toString(boolean comparible)
	{
		//don't compare head values when organizing the lines alphabetically
		if(comparible)
			return super.toString(comparible);
		String str = super.toString(comparible);
		if(!this.heads.isEmpty())
		{
			str += " = ";
			if(this.lbracket != ' ')
				str += "" + this.lbracket;
			for(Object obj : this.heads)
				str += obj.toString() + ",";
			str = str.substring(0, str.length()-1);
			if(this.rbracket != ' ')
				str += "" + this.rbracket;
		}
		return str;
	}
	/**
	 * Recursively populate the array from string to actual objects
	 */
	public void parseHead(String[] str,List<Object> list) 
	{
		for(String s : str)
		{
			if(s.startsWith("" + this.lbracket) && this.lbracket != ' ')
			{
				List<Object> newList = new ArrayList<Object>();
				list.add(newList);
				//step 1 remove braces
				s = s.trim();
				s = s.substring(1, s.length()-1);
				//step 2 select string
				String[] select = LineUtil.selectString(s, ',', this.quote,this.lbracket,this.rbracket);
				this.parseHead(select, newList);
			}
			else
				list.add(parseWeight(s));
		}
	}
	/**
	 * get the primitive object from the string
	 */
	public Object parseWeight(String weight) 
	{
		return LineUtil.parseWeight(weight, this.quote);
	}
	
	public static class Entry
	{
		public Object obj;
		public char id;
		public Entry(Object obj)
		{
			 this(obj,' ');
		}
		public Entry(Object obj,char c)
		{
			this.obj = obj;
			this.id = c;
		}
		@Override
		public String toString()
		{
			if(this.obj instanceof Number)
			{
				return this.obj.toString() + (this.id == ' ' ? "" : this.id);
			}
			else if(this.obj instanceof String)
			{
				String quote = "";
				if(this.id != ' ')
					quote += this.id;
				return quote + this.obj.toString() + quote;
			}
			return this.obj.toString();
		}
	}
	
}
