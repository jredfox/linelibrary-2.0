package com.EvilNotch.lib.util.line.comment;

public class Comment implements IComment{
	
	public char cStart;
	public String comment;
	public boolean attatched;
	/**
	 * the initial line index it's parsed upon won't be updated nor synced
	 */
	public int index;
	
	public Comment(char c,String comment,int index)
	{
		this(c,comment,false,index);
	}
	public Comment(char c,String comment,boolean attatched,int index)
	{
		this.cStart = c;
		this.comment = comment.substring(comment.indexOf(c)+1, comment.length());
		this.attatched = attatched;
		this.index = index;
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
	public boolean isAttatched() {
		return this.attatched;
	}
	
	@Override
	public String toString()
	{
		return this.cStart + this.comment;
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Comment))
			return false;
		Comment c = (Comment)obj;
		return this.toString().equals(c.toString());
	}
}
