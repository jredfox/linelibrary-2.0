package com.EvilNotch.lib.util.line;

import java.util.ArrayList;
import java.util.List;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.config.Comment;
import com.EvilNotch.lib.util.line.config.IComment;

public class Line implements ILineSeperation,ILineComment{
	
	public String modid;
	public String name;
	
	public char seperator;
	public char quote;
	public boolean hasQuote;
	/**
	 * list of comments attached to the list
	 */
	public List<IComment> comments = new ArrayList<IComment>();
	
	public Line(String str)
	{
		this(str,':','"');
	}
	
	public Line(String str,char sep,char q)
	{
		this.seperator = sep;
		this.quote = q;
		
		if(str.contains("" + this.quote))
		{
			str = JavaUtil.parseQuotes(str, 0, "" + this.quote);
			this.hasQuote = true;
		}
		
		String[] parts = JavaUtil.splitFirst(str, sep);
		this.modid = parts[0].trim();
		
		//parse the name
		if(parts.length == 2)
		{
			if(this.hasQuote)
			{
				this.name = parts[1].trim();
			}
			else
			{
				StringBuilder builder = new StringBuilder();
				String restOfLine = parts[1].trim();
				for(char c : restOfLine.toCharArray())
				{
					if(c == ' ')
						break;
					builder.append(c);
				}
				this.name = builder.toString();
			}
		}
	}

	@Override
	public String getId()
	{
		String id = this.modid;
		if(this.name != null)
		{
			id += ":" + this.name;
		}
		return id;
	}
	
	@Override
	public char getSeprator() {
		return this.seperator;
	}
	@Override
	public char getQuote(){
		return this.quote;
	}
	
	//regular java stuffs start here
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof ILine))
			return false;
		ILine line = (ILine)obj;
		return this.getId().equals(line.getId());
	}
	/**
	 * speed up the config process
	 */
	@Override
	public int hashCode()
	{
		return this.getId().hashCode();
	}
	@Override
	public String toString()
	{
		return this.toString(false);
	}
	@Override
	public String getComparible() 
	{
		return this.toString(true);
	}
	/**
	 * allow to toString() method to be based upon whether or not it should have quotes and stuff
	 */
	public String toString(boolean comparible) 
	{
		String str = this.getId();
		if(this.hasQuote && !comparible)
			str = "" + this.quote + str + this.quote;
		return str;
	}

	@Override
	public void addComment(IComment c) {
		this.comments.add(c);
	}

	@Override
	public void removeComment(IComment c) {
		this.comments.remove(c);
	}

	@Override
	public List<IComment> getComments() {
		return this.comments;
	}

}
