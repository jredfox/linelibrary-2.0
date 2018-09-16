package com.EvilNotch.lib.util.line.comment;

public interface IComment {
	
	public char getCommentStart();
	public String getComment();
	/**
	 * is it directly attached to the object such as a line or header
	 */
	public boolean isAttatched();

}
