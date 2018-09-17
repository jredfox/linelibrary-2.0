package com.EvilNotch.lib.util.line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.EvilNotch.lib.util.line.comment.ICommentAttatch;
import com.EvilNotch.lib.util.line.util.LineUtil;

public class LineDynamicLogic extends LineComment implements ILine,ILineComment{

	public HashMap<Integer,List<ILine>> lines = new HashMap<Integer,List<ILine>>();
	
	public LineDynamicLogic(String str)
	{
		String[] ores = LineUtil.selectString(str, "||", '"', "<{", ">}");
		for(int oreIndex=0;oreIndex<ores.length;oreIndex++)
		{
			String section = ores[oreIndex];
			String[] parts = LineUtil.selectString(section, ",", '"',  "<{", ">}");
			
			List<ILine> list = new ArrayList<ILine>();
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
	
	public List<ILine> getLinesAtPos(int pos)
	{
		return this.lines.get(pos);
	}

	@Override
	public String getComparible() 
	{
		return this.toString(true);
	}
	
	@Override
	public String toString()
	{
		return this.toString(false);
	}

	public String toString(boolean comparible) 
	{
		StringBuilder b = new StringBuilder();
		for(int section : this.lines.keySet())
		{
			List<ILine> lines = this.lines.get(section);
			for(int i=0;i<lines.size();i++)
			{
				ILine l = lines.get(i);
				boolean inRange = i+1 < lines.size();
				String comma = inRange ? ", " : "";
				if(comparible)
					b.append(l.getComparible() + comma);
				else
					b.append(l.toString() + comma);
			}
			if(section+1 < this.lines.size())
				b.append(" || ");
		}
		return b.toString();
	}

}
