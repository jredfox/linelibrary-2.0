package com.EvilNotch.lib.util.line.util;

import com.EvilNotch.lib.util.line.ILine;
import com.EvilNotch.lib.util.line.Line;
import com.EvilNotch.lib.util.line.LineArray;
import com.EvilNotch.lib.util.line.LineMeta;

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

}
