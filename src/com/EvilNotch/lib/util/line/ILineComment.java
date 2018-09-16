package com.EvilNotch.lib.util.line;

import java.util.List;

import com.EvilNotch.lib.util.line.comment.IComment;
/**
 * if your line supports comments it will save it this way
 * @author jredfox
 *
 */
public interface ILineComment extends ILine{
	
	public void addComment(IComment c);
	public void removeComment(IComment c);
	public List<IComment> getComments();

}
