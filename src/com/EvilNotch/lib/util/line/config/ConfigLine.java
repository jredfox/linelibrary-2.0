package com.EvilNotch.lib.util.line.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.ILine;
import com.EvilNotch.lib.util.line.ILineMeta;
import com.EvilNotch.lib.util.line.Line;
import com.EvilNotch.lib.util.line.LineArray;
import com.EvilNotch.lib.util.line.LineMeta;
import com.EvilNotch.lib.util.line.comment.Comment;
import com.EvilNotch.lib.util.line.comment.IComment;

public class ConfigLine extends ConfigBase{
	
	/**
	 * seperator for all the lines that use them
	 */
	public char sep = ':';
	public char quote = '"';
	/**
	 * brackets for metadata of LineMeta
	 */
	public char[] metaBrackets = new char[]{'<','>'};
	/**
	 * brackets for LineArray defaults
	 */
	public char[] arrBrackets = new char[]{'[',']'};
	
	public ConfigLine(String inputStream,File output)
	{
		super(inputStream,output);
	}
	
	public ConfigLine(File f) 
	{
		super(f);
	}
	
	public ConfigLine(File f,String header,char commentStart,List<String> comments)
	{
		this(f,header,true,commentStart,comments,"</>".toCharArray(),':','"',"<>".toCharArray(),"[]".toCharArray());
	}
	public ConfigLine(File f,String header,boolean allowComments,char commentStart,List<String> comments,char[] headerWrappers,char sep,char q,char[] metaBrackets,char[] arrBrackets)
	{
		super(f,header,allowComments,commentStart,comments,headerWrappers);
		this.sep = sep;
		this.quote = q;
		this.metaBrackets = metaBrackets;
		this.arrBrackets = arrBrackets;
	}
	
	/**
	 * get a line from string
	 */
	@Override
	public ILine getLineFromString(String str) 
	{
		if(str.contains("="))
		{
			return new LineArray(str,this.sep,this.quote,new String(this.metaBrackets),this.arrBrackets);
		}
		else if(str.contains("" + this.metaBrackets[0]) || str.contains("{"))
			return new LineMeta(str,this.sep,this.quote,new String(this.metaBrackets));

		return new Line(str,this.sep,this.quote);
	}
}
