package com.EvilNotch.lib.util.line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.EvilNotch.lib.util.line.util.LineUtil;

public class LineDynamicLogic implements ILine{

	public HashMap<Integer,List<ILine>> lines = new HashMap();
	
	public LineDynamicLogic(String str)
	{
		String[] ores = str.split("=");
		for(int oreIndex=0;oreIndex<ores.length;oreIndex++)
		{
			String section = ores[oreIndex];
			String[] parts = section.split(",");
			
			List<ILine> list = new ArrayList();
			for(String line : parts)
			{
				ILine l = LineUtil.getLineFromString(line);
				list.add(l);
			}
			this.lines.put(oreIndex,list);
		}
	}
	
	@Override
	public String getId() 
	{
		return this.getLinesAtPos(0).get(0).getId();
	}
	
	public List<ILine> getLinesAtPos(int pos){
		return this.lines.get(pos);
	}

	@Override
	public String getComparible() 
	{
		return this.toString(true);
	}

	public String toString(boolean comparible) 
	{
		return null;
	}

}
