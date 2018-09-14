package com.EvilNotch.lib.util.line.config;

public class Comment implements IComment{
	
	public char cStart;
	public String comment;
	
	public Comment(char c,String comment)
	{
		this.cStart = c;
	}
	@Override
	public char getCommentStart()
	{
		return this.cStart;
	}
	@Override
	public String getComment() 
	{
		return this.comment;
	}
	
	@Override
	public String toString()
	{
		return this.cStart + this.comment;
	}
}
