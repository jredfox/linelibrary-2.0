package com.EvilNotch.lib.util.line.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.ILine;
import com.EvilNotch.lib.util.line.ILineMeta;
import com.EvilNotch.lib.util.line.Line;
import com.EvilNotch.lib.util.line.LineArray;
import com.EvilNotch.lib.util.line.LineMeta;

public class ConfigLine {
	
	public List<ILine> lines = new ArrayList<ILine>();
	public File file = null;
	public char commentStart = '#';
	
	public ConfigLine(String inputStream,File output)
	{
		this.file = output;
		this.readConfigFromJar(inputStream);
	}

	public ConfigLine(File f)
	{
		this.file = f;
		this.readConfig();
	}
	
	public void readConfig()
	{
		this.lines.clear();
		try
		{
			List<String> list = JavaUtil.getFileLines(this.file,true);
			parseLines(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void readConfigFromJar(String stream) 
	{
		this.lines.clear();
		try
		{
			List<String> list = JavaUtil.getFileLines(stream);
			parseLines(list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * calling this directly will save the config to the disk/input stream
	 */
	public void saveConfig()
	{
		List<String> list = new ArrayList<String>();
		for(ILine line : this.lines)
			list.add(line.toString());
		try
		{
			JavaUtil.saveFileLines(list, this.file, true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * parse the lines from the file
	 */
	protected void parseLines(List<String> list) 
	{
		removeBOM(list);
		for(String str : list)
		{
			str = str.trim();
			if(JavaUtil.toWhiteSpaced(str).equals("") || str.startsWith("<"))
				continue;
			int index = str.indexOf(this.commentStart);
			if(index == 0)
				continue;
			str = removeComments(str);
			this.lines.add(getLineFromString(str));
		}
	}
	
    /**
     * Removes UTF-8 Byte Order Marks for windows note pad compatibility
     */
    public void removeBOM(List<String> list) 
    {
		for(int i=0;i<list.size();i++)
		{
			String s = list.get(i);
			if(s.length() > 0)
			{
				if((int)s.charAt(0) == 65279)
					s = s.substring(1, s.length());
				list.set(i, s);
			}
		}
	}
	public String removeComments(String strline) 
	{
		int index = strline.indexOf(this.commentStart);
		if(index == -1)
			return strline;
		
		return strline.substring(0, index);
	}
	
	/**
	 * get a line from string
	 */
	public ILine getLineFromString(String str) 
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
	 * add a line if and only if it doesn't exist
	 */
	public boolean addLine(ILine line)
	{
		if(!this.containsLine(line,true))
		{
			return this.lines.add(line);
		}
		return false;
	}
	/**
	 * see if there is another line or not here already by comparing it's id and possibly metadata
	 */
	public boolean containsLine(ILine c,boolean compareMeta) 
	{
		return this.getLineIndex(c, compareMeta) != -1;
	}
	/**
	 * get the line as an index in the file you can't remove meta lines without them
	 * @return -1 if doesn't exist
	 */
	public int getLineIndex(ILine c,boolean compareMeta)
	{
		for(int i=0;i<this.lines.size();i++)
		{
			ILine l = this.lines.get(i);
			if(l.equals(c))
			{
				if(!compareMeta)
					return i;
				if(!(l instanceof ILineMeta) && c instanceof LineMeta || l instanceof ILineMeta && !(c instanceof ILineMeta) )
					continue;
				ILineMeta line = (ILineMeta)l;
				ILineMeta compare = (ILineMeta)c;
				if(line.equalsMeta(compare) && compare.equalsMeta(line))
					return i;
			}
		}
		return -1;
	}

	public void appendLine(ILine line)
	{
		this.lines.add(line);
	}
	
	public void removeLine(ILine line)
	{
		this.removeLine(line,true);
	}
	
	public void removeLine(ILine line,boolean compareMeta)
	{
		int index = this.getLineIndex(line, compareMeta);
		if(index != -1)
			this.lines.remove(index);
	}
	/**
	 * set a line at it's index if doesn't exist add a line
	 */
	public void setLine(ILine line)
	{
		int index = this.getLineIndex(line, true);
		if(index != -1)
			this.lines.set(index,line);
		else
			this.lines.add(line);
	}
	/**
	 * for printlines do not use this for parsing to/from disk for actual files as strings can only hold so many chars
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for(ILine line : this.lines)
			builder.append(line + "\r\n");
		String str = builder.toString();
		str = str.substring(0, str.length()-2);
		return str;
	}

}
