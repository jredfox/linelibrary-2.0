package com.EvilNotch.lib.util.line.util;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.ILine;
import com.EvilNotch.lib.util.line.Line;
import com.EvilNotch.lib.util.line.LineArray;
import com.EvilNotch.lib.util.line.LineMeta;
import com.EvilNotch.lib.util.line.LineArray.Entry;

public class LineUtil {
	
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
}
