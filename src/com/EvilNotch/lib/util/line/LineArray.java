package com.EvilNotch.lib.util.line;

import java.util.ArrayList;
import java.util.List;

import com.EvilNotch.lib.util.JavaUtil;

public class LineArray extends LineMeta implements ILineHeadArray{

	public List<Object> heads = new ArrayList<Object>();
	public char lbracket = ' ';
	public char rbracket = ' ';
	
	public LineArray(String str)
	{
		this(str,':','"');
	}
	
	public LineArray(String str, char sep,char q) 
	{
		super(str, sep,q);
		if(str.contains("="))
		{
			str = JavaUtil.splitFirst(str,'=')[1].trim();
			if(str.startsWith("(") || str.startsWith("["))
			{
				this.lbracket = str.charAt(0);
				this.rbracket = str.charAt(str.length()-1);
				str = str.substring(1, str.length()-1);
			}
			String[] toParse = str.split(",");
			for(String s : toParse)
			{
				parseObject(s.trim());
			}
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
	public Object getHeadAtIndex(int index) 
	{
		Object obj = this.heads.get(index);
		if(obj instanceof Entry)
			obj = ((Entry)obj).obj;
		return obj;
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

	public void parseObject(String str) 
	{
		this.heads.add(parseWeight(str));
	}
	/**
	 * get the primitive object from the string
	 */
	public Object parseWeight(String weight) 
	{
		String whitespaced = JavaUtil.toWhiteSpaced(weight);
		String lowerCase = whitespaced.toLowerCase();
		int size = whitespaced.length();
		
		if(lowerCase.equals("true") || lowerCase.equals("false"))
		{
			return new Entry(Boolean.parseBoolean(whitespaced));
		}
		if(JavaUtil.isStringNum(whitespaced))
		{
			char idNum = ' ';
			String lastChar = lowerCase.substring(size-1, size);
			boolean hasId = "bslfdi".contains(lastChar);
			boolean flag = "BSLFDI".contains(whitespaced.substring(size-1, size));
			boolean dflag = whitespaced.contains(".");
			if(hasId)
				idNum = lastChar.charAt(0);
			
			String num = hasId ? whitespaced.substring(0, whitespaced.length()-1) : whitespaced;
			Object obj = null;
			
			//do general first
			if(idNum == ' ' && !dflag){
				obj = JavaUtil.castInt(Long.parseLong(num));//if greater then max value it equals max value
			}
			//byte
			else if(idNum == 'b'){
				obj = Byte.parseByte(num);
			}
			else if(idNum == 's'){
				obj = Short.parseShort(num);
			}
			else if(idNum == 'l'){
				obj = Long.parseLong(num);
			}
			else if(idNum == 'i'){
				obj = Integer.parseInt(num);
			}
			else if(idNum == 'f'){
				obj = Float.parseFloat(num);
			}
			else if(idNum == 'd'){
				obj = Double.parseDouble(num);
			}
			else if(dflag){
				obj = Double.parseDouble(num);
			}
			if(flag)
				idNum = JavaUtil.toUpperCaseChar(idNum);
			return  new Entry(obj,idNum);
		}
		return new Entry(JavaUtil.parseQuotes(weight, 0, "" + this.quote),this.quote);
	}
	
	public class Entry
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
	/**
	 * set the lines value
	 */
	@Override
	public void setObject(Object obj,int index) 
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
	
}
