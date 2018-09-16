package com.EvilNotch.lib.util.line.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.line.ILine;
import com.EvilNotch.lib.util.line.ILineComment;
import com.EvilNotch.lib.util.line.ILineMeta;
import com.EvilNotch.lib.util.line.Line;
import com.EvilNotch.lib.util.line.LineArray;
import com.EvilNotch.lib.util.line.LineMeta;
import com.EvilNotch.lib.util.line.comment.Comment;
import com.EvilNotch.lib.util.line.comment.IComment;

public class ConfigLine {
	
	public List<ILine> lines = new ArrayList<ILine>();
	public File file = null;
	public String stream = null;
	
	/**
	 * this is what signify's a comment for the config start
	 */
	public char commentStart = '#';
	/**
	 * this tells whether or not config comments get parsed saved added or written to the disk
	 */
	public boolean commentsEnabled = true;
	/**
	 * this is the last text file read when parsing also determines if config can save to disk saves ms
	 */
	public List<String> origin = new ArrayList<String>();
	/**
	 * comments above the header
	 */
	public List<Comment> headerComments = new ArrayList<Comment>();
	/**
	 * a list of comments that starts below the ffile
	 */
	public List<Comment> footerComments = new ArrayList<Comment>();
	/**
	 * temporary storage for comments during parsing
	 */
	protected List<Comment> tmpComments = new ArrayList<Comment>();
	/**
	 * fancy header for example "<"DungeonMobs">"
	 */
	public String header = "";
	public char[] headerWrappers = new char[]{'<','/','>'};
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
	
	/**
	 * call this constructor if your reading from a jar/zip 
	 * since jar files can't be modified only call this constructor if the output file doesn't exist
	 */
	public ConfigLine(String inputStream,File output)
	{
		this.file = output;
		this.stream = inputStream;
	}

	public ConfigLine(File f)
	{
		this.file = f;
	}
	public ConfigLine(File f,String header,char commentStart,List<String> comments)
	{
		this(f,header,commentStart,comments,"</>".toCharArray(),':','"',"<>".toCharArray(),"[]".toCharArray());
	}
	public ConfigLine(File f,String header,char commentStart,List<String> comments,char[] headerWrappers,char sep,char q,char[] metaBrackets,char[] arrBrackets)
	{
		this.file = f;
		if(header != null)
			this.header = header;//non null values accepted
		this.commentStart = commentStart;
		for(String s : comments)
			this.addHeaderComment(s);
		this.sep = sep;
		this.quote = q;
		this.headerWrappers = headerWrappers;
		this.metaBrackets = metaBrackets;
		this.arrBrackets = arrBrackets;
	}
	/**
	 * call this to load the config from the disk manual 
	 * call after constructing is now called as this is more proper
	 */
	public void loadConfig()
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
	/**
	 * if you used the jar constructor call this instead of loadConfig() regular
	 */
	public void loadConfigFromJar(){
		this.loadConfigFromJar(this.stream);
	}
	
	public void loadConfigFromJar(String inputStream) 
	{
		this.lines.clear();
		try
		{
			List<String> list = JavaUtil.getFileLines(inputStream);
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
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void saveConfig()
	{
		this.saveConfig(false);
	}
	
	public void saveConfig(boolean alphabitize)
	{
		this.saveConfig(alphabitize,false,true);
	}
	/**
	 * doesn't call alphabetize and no message will appear either. This is direct saveToDisk() method
	 */
	public void saveToDisk()
	{
		this.saveConfig(false, true, false);
	}
	/**
	 * save config if and only if lines are different or it's forceably saved
	 */
	public void saveConfig(boolean alphabitize,boolean force,boolean msg)
	{
		if(alphabitize)
			this.alphabitize();
		List<String> list = this.toFileLines();
		if(force || !list.equals(this.origin))
		{
			if(msg)
				System.out.println("Saving Config:" + this.file);
			this.saveConfig(list);
			this.origin = list;
		}
	}
	/**
	 * equivalent to toString() but, has capacity for more indexes then a single string
	 * @return
	 */
	public List<String> toFileLines() 
	{
		List<String> list = new ArrayList<String>();
		for(IComment c : this.headerComments)
			list.add(c.toString());
		if(!this.headerComments.isEmpty())
			list.add("");
		if(!this.header.isEmpty())
			list.add(this.headerWrappers[0] + this.header + this.headerWrappers[2] + "\r\n");
		
		for(ILine l : this.lines)
		{
			if(l instanceof ILineComment && this.commentsEnabled)
			{
				ILineComment line = (ILineComment)l;
				String attatched = "";
				for(IComment c : line.getComments())
				{
					if(!c.isAttatched())
						list.add(c.toString());
					else
						attatched += c.toString();
				}
				list.add(line.toString() + attatched);				
			}
			else
				list.add(l.toString());
		}
		if(!this.header.isEmpty())
			list.add("\r\n" + this.headerWrappers[0] + JavaUtil.toWhiteSpaced("" + this.headerWrappers[1]) + this.header + this.headerWrappers[2]);
		if(!this.footerComments.isEmpty())
		{
			list.add("");//force new line
			for(IComment c : this.footerComments)
				list.add(c.toString());
		}
		return list;
	}
	/**
	 * ever wanted to alphabitize a file well go ahead you know you want perfection
	 */
	public void alphabitize() {
		Collections.sort(this.lines);
	}
	/**
	 * mess with people's ocd here
	 */
	public void shuffle(){
		Collections.shuffle(this.lines);
	}
	/**
	 * specify specific random to be the same shuffle every time
	 */
	public void shuffle(Random rnd){
		Collections.shuffle(this.lines,rnd);
	}
	/**
	 * piss the hell out of people
	 */
	public void resetConfig(){
		this.lines.clear();
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
		
		int index_line = 0;
		boolean passedHeader = false;
		for(String str : list)
		{
			str = str.trim();
			
			if(this.commentsEnabled)
			{
				String whitespaced = JavaUtil.toWhiteSpaced(str);
				if(whitespaced.equals("") || whitespaced.startsWith("" + this.headerWrappers[0]) && whitespaced.endsWith("" + this.headerWrappers[2]))
				{
					passedHeader = true;
					continue;
				}
				int index = str.indexOf(this.commentStart);
				if(index == 0)
				{
					if(passedHeader)
						this.tmpComments.add(new Comment(this.commentStart,str.substring(1, str.length()),index_line));
					else
					{
						//add custom header comments
						Comment c = new Comment(this.commentStart,str.substring(1, str.length()),-1);
						if(!this.headerComments.contains(c))
							this.headerComments.add(c);
					}
					continue;
				}
				else if(index != -1)
				{
					this.tmpComments.add(new Comment(this.commentStart,str.substring(index, str.length()),true,index_line));
				}
			}
			str = removeComments(str);
			this.lines.add(getLineFromString(str));
			index_line++;
		}
		if(this.commentsEnabled)
		{
			for(Comment c : this.tmpComments)
			{
				if(c.index == this.lines.size())
				{
					this.footerComments.add(c);
					continue;
				}
				ILine line = this.lines.get(c.index);
				c.index = -1;
				((ILineComment)line).addComment(c);
			}
			this.tmpComments.clear();
		}
		this.origin = this.toFileLines();
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
			return new LineArray(str,this.sep,this.quote,new String(this.metaBrackets),this.arrBrackets);
		}
		else if(str.contains("" + this.metaBrackets[0]) || str.contains("{"))
			return new LineMeta(str,this.sep,this.quote,new String(this.metaBrackets));

		return new Line(str,this.sep,this.quote);
	}
	/**
	 * add a line if and only if it doesn't exist
	 */
	public boolean addLine(ILine line)
	{
		if(!this.containsLine(line,this.checkMetaByDefault()))
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
	 * get the updated line from generated one
	 */
	public ILine getUpdatedLine(ILine l){
		return this.getUpdatedLine(l, this.checkMetaByDefault());
	}
	/**
	 * get the updated line from generated one
	 */
	public ILine getUpdatedLine(ILine l,boolean compareMeta)
	{
		int index = this.getLineIndex(l, compareMeta);
		if(index != -1)
			return this.lines.get(index);
		return l;
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
		this.removeLine(line,this.checkMetaByDefault());
	}
	
	public void removeLine(ILine line,boolean compareMeta)
	{
		int index = this.getLineIndex(line, compareMeta);
		if(index != -1)
		{
			this.lines.remove(index);
		}
	}
	
	public void preserveLineComments(ILine newLine,ILine oldLine)
	{
		if(!(newLine instanceof ILineComment) || !(oldLine instanceof ILineComment))
			return;
		this.preserveLineComments((ILineComment)newLine,(ILineComment)oldLine);
	}
	public void preserveLineComments(ILineComment newLine, ILineComment oldLine) 
	{
		List<IComment> comments = newLine.getComments();
		for(IComment c : oldLine.getComments())
			if(!comments.contains(c))
				newLine.addComment(c);
	}
	/**
	 * set a line at it's index if doesn't exist add a line
	 */
	public void setLine(ILine line)
	{
		int index = this.getLineIndex(line, this.checkMetaByDefault());
		if(index != -1)
		{
			ILine olde = this.lines.set(index,line);
			this.preserveLineComments(line,olde);
		}
		else
			this.appendLine(line);
	}
	/**
	 * add a comment to a line
	 */
	public void addLineComment(ILine line,String comment)
	{
		if(!this.commentsEnabled)
			return;
		if(!this.containsLine(line,this.checkMetaByDefault() ))
			return;
		line = this.getUpdatedLine(line);
		ILineComment comments = (ILineComment)line;
		comments.addComment(new Comment(this.commentStart,comment,-1));
	}
	/**
	 * add a comment to the top of the file before the lines
	 * @param comment
	 */
	public void addHeaderComment(String comment)
	{
		if(!this.commentsEnabled)
			return;
		this.headerComments.add(new Comment(this.commentStart,comment,-1));
	}
	
	/**
	 * add comment below all the lines and header if any
	 * @param comment
	 */
	public void addFooterComment(String comment)
	{
		if(!this.commentsEnabled)
			return;
		this.footerComments.add(new Comment(this.commentStart,comment,-1));
	}
	/**
	 * remove a comment below file lines
	 * @param comment
	 */
	public void removeFooterComment(String comment)
	{
		if(!this.commentsEnabled)
			return;
		this.footerComments.remove(new Comment(this.commentStart,comment,-1));
	}
	/**
	 * remove a comment at above file lines
	 * @param comment
	 */
	public void removeHeaderComment(String comment)
	{
		if(!this.commentsEnabled)
			return;
		this.headerComments.remove(new Comment(this.commentStart,comment,-1));
	}
	/**
	 * remove a comment from a line
	 */
	public void removeLineComment(ILine line,String comment)
	{
		if(!this.commentsEnabled)
			return;
		if(!this.containsLine(line,this.checkMetaByDefault() ))
			return;
		line = this.getUpdatedLine(line);
		ILineComment comments = (ILineComment)line;
		comments.removeComment(new Comment(this.commentStart,comment,-1));
	}
	
	public List<IComment> getCommentsFromLine(ILine line) 
	{
		line = this.getUpdatedLine(line);
		if(line instanceof ILineComment)
		{
			return ((ILineComment)line).getComments();
		}
		return null;
	}

	/**
	 * for printlines do not use this for parsing to/from disk for actual files as strings can only hold so many chars
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for(String s : this.toFileLines())
			builder.append(s + "\r\n");
		return builder.toString();
	}
	/**
	 * tells whether or not the config will check meta by default when adding/setting/removing lines 
	 * set to false if meta is only used as a config option not line identifier as well like powered creepers
	 */
	public boolean checkMetaByDefault()
	{
		return true;
	}

}
