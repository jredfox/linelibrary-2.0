package com.EvilNotch.lib.util.line.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.ILine;
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
			int index = str.indexOf(this.commentStart);
			if(index == 0)
				continue;
			str = removeComments(str);
			this.lines.add(getLineFromString(str));
		}
	}
    /**
     * Removes UTF-8 Byte Order Marks for windows note pad compatability
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
	
	public void addLine(ILine line)
	{
		if(!this.containsLine(line))
			this.lines.add(line);
	}
	@Deprecated
	public boolean containsLine(ILine line) 
	{
		return false;
	}

	public void appendLine(ILine line)
	{
		this.lines.add(line);
	}
	@Deprecated
	public void removeLine(ILine line)
	{
		this.lines.remove(line);
	}

}
