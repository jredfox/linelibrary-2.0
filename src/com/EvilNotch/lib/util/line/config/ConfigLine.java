package com.EvilNotch.lib.util.line.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.ILine;
import com.EvilNotch.lib.util.line.ILineMeta;
import com.EvilNotch.lib.util.line.Line;
import com.EvilNotch.lib.util.line.LineArray;
import com.EvilNotch.lib.util.line.LineMeta;

public class ConfigLine {
	
	public List<ILine> lines = new ArrayList<ILine>();
	public File file = null;
	/**
	 * this is what signifys a comment for the config start
	 */
	public char commentStart = '#';
	/**
	 * this is to see if the config needs to save or not
	 */
	public List<String> origin = new ArrayList<String>();
	/**
	 * comments above the header
	 */
	public List<Comment> headerComments = new ArrayList<Comment>();
	/**
	 * comments attached to the lines either above or directly in front
	 */
	public List<Comment> comments = new ArrayList<Comment>();
	/**
	 * fancy header for example "<"DungeonMobs">"
	 */
	public String header = "";
	public char[] headerWrappers = new char[]{'<','/','>'};
	
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
		this.comments.clear();
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
		this.comments.clear();
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
	protected void saveConfig(List<String> list)
	{
		try
		{
			JavaUtil.saveFileLines(list, this.file, true);
			this.origin = list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void saveConfig(boolean alphabitize)
	{
		List<String> list = new ArrayList<String>();
		if(alphabitize)
			this.alphabitize();
		for(Comment c : this.headerComments)
			list.add(c.toString());
		if(!this.headerComments.isEmpty())
			list.add("\r\n\r\n");
		if(!this.header.isEmpty())
			list.add(this.headerWrappers[0] + this.header + this.headerWrappers[2] + "\r\n\r\n");
		for(ILine line : this.lines)
			list.add(line.toString());
		if(!this.header.isEmpty())
			list.add("\r\n\r\n" + this.headerWrappers[0] + this.headerWrappers[1] + this.header + this.headerWrappers[2]);
		
		if(!list.equals(this.origin))
			this.saveConfig(list);
	}
	
	public void alphabitize() {
		Collections.sort(this.lines);
	}
	public void shuffle(){
		Collections.shuffle(this.lines);
	}
	public void shuffle(Random rnd){
		Collections.shuffle(this.lines,rnd);
	}
	/**
	 * don't use this unless you want all data getting cleared
	 */
	public void resetConfig(){
		this.lines.clear();
		this.comments.clear();
		this.readConfig();
	}
	/**
	 * restore it to the last time the config was read or saved to/from the disk
	 */
	public void restoreConfig(){
		this.saveConfig(this.origin);
	}

	/**
	 * parse the lines from the file
	 */
	protected void parseLines(List<String> list) 
	{
		removeBOM(list);
		this.origin = list;
		for(String str : list)
		{
			str = str.trim();
			String whitespaced = JavaUtil.toWhiteSpaced(str);
			if(whitespaced.equals("") || whitespaced.startsWith(this.header) && !this.header.isEmpty())
				continue;
			int index = str.indexOf(this.commentStart);
			if(index == 0)
			{
				this.comments.add(new Comment(this.commentStart,str));
				continue;
			}
			else if(index != -1)
			{
				this.comments.add(new Comment(this.commentStart,str.substring(index, str.length())));
			}
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
