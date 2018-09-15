package com.EvilNotch.lib.util.line;

import java.util.List;

import com.EvilNotch.lib.util.line.config.IComment;

public interface ILineComment extends ILine{
	
	public void addComment(IComment c);
	public void removeComment(IComment c);
	public List<IComment> getComments();

}
