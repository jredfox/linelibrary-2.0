package com.EvilNotch.lib.util.line.util;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.ILine;
import com.EvilNotch.lib.util.line.Line;
import com.EvilNotch.lib.util.line.LineArray;
import com.EvilNotch.lib.util.line.LineMeta;
import com.EvilNotch.lib.util.line.LineArray.Entry;

public class LineUtil {
	/**
	 * use getLinefromString(String str,char sep,char quote,char[] metaBrackets,char[] arrBrackets) instead
	 */
	@Deprecated
	public static ILine getLineFromString(String str)
	{
		if(str.contains("="))
		{
			return new LineArray(str);
		}
		else if(str.contains("<") || str.contains("{"))
			return new LineMeta(str);

		return new Line(str);
	}
	
	/**
	 * get the primitive object from the string
	 */
	public static Entry parseWeight(String weight,char quote) 
	{
		String whitespaced = JavaUtil.toWhiteSpaced(weight);
		String lowerCase = whitespaced.toLowerCase();
		int size = whitespaced.length();
		
		if(lowerCase.equals("true") || lowerCase.equals("false"))
		{
			return new Entry(Boolean.parseBoolean(whitespaced));
		}
		else if(JavaUtil.isStringNum(whitespaced))
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
				obj = Integer.parseInt(num);//if greater then max value it equals max value
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
		return weight.contains("" + quote) ? new Entry(JavaUtil.parseQuotes(weight, 0, "" + quote),quote) : new Entry(weight.trim());
	}
	
	/**
	 * string filter more sophisticated then standard split. It filters out the char your looking for only if it's not in quotes or in brackets then splits them
	 * skipping over any brackets
	 */
	public static String[] selectString(String input,char split,char q,char lbracket,char rbracket)
	{
		StringBuilder builder = new StringBuilder();
		boolean insideQuote = false;
		for(int i=0;i<input.length();i++)
		{
			char c = input.charAt(i);
			
			if(c == q && c != ' ')
				insideQuote = !insideQuote;
			
			if(c == lbracket && c != ' ' && !insideQuote)
			{
				int rBracket = getRightBracket(i,input,lbracket,rbracket);
				builder.append(input.substring(i,rBracket+1));
				i = rBracket;
				continue;//continue will force i++ thus you need the varible = to what it should be next
			}
			
			if(c == split && !insideQuote)
			{
				builder.append(JavaUtil.uniqueSplitter);
			}
			else
			{
				builder.append(c);
			}
		}
		return builder.toString().split(JavaUtil.uniqueSplitter);
	}
	/**
	 * split a string with it skipping things inside of quotes and insideof lrbrackets
	 */
	public static String[] selectString(String input,String split,char q,String lbrackets,String rbrackets)
	{
		StringBuilder builder = new StringBuilder();
		boolean insideQuote = false;
		for(int i=0;i<input.length();i++)
		{
			char c = input.charAt(i);
			
			if(c == q)
				insideQuote = !insideQuote;
			
			if(split.contains("" + c) && !insideQuote)
			{
				int rIndex = i+split.length();
				if(rIndex > input.length())
					rIndex--;
				String s = input.substring(i, i+split.length());
				if(split.equals(s))
				{
					builder.append(JavaUtil.uniqueSplitter);
					i += split.length()-1;
					continue;
				}
			}
			
			if(lbrackets.contains("" + c) && !insideQuote)
			{
				int index = lbrackets.indexOf(c);
				char lbracket = lbrackets.charAt(index);
				char rbracket = rbrackets.charAt(index);
				
				int rBracket = getRightBracket(i,input,lbracket,rbracket);
				builder.append(input.substring(i,rBracket+1));
				
				i = rBracket;
				continue;//continue will force i++ thus you need the varible = to what it should be next
			}
			builder.append(c);
		}
		return builder.toString().split(JavaUtil.uniqueSplitter);
	}
	
	public static int getRightBracket(int lindex,String str,char lbracket,char rbracket) 
	{
    	int lb = 0;
    	for(int i=lindex;i<str.length();i++)
    	{
    		String ch = str.substring(i, i+1);
			
    		if(ch.equals("" + lbracket))
    			lb++;
    		else if(ch.equals("" + rbracket))
    			lb--;
    		if(lb == 0)
    			return i;
    	}
		return -1;
	}
}
