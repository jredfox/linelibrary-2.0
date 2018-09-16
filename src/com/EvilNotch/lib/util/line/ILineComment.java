package com.EvilNotch.lib.util.line;

import java.util.List;

import com.EvilNotch.lib.util.line.comment.IComment;
import com.EvilNotch.lib.util.line.comment.ICommentAttatch;
/**
 * if your line supports comments it will save it this way
 * @author jredfox
 *
 */
public interface ILineComment extends ILine{
	
	public void addComment(ICommentAttatch c);
	public void removeComment(ICommentAttatch c);
	public List<ICommentAttatch> getComments();

}
