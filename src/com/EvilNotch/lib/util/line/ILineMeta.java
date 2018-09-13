package com.EvilNotch.lib.util.line;

public interface ILineMeta extends ILine{
	
	/**
	 * Don't return null return new String[]{""} instead if you have to
	 * @return An array of data that isn't the identifier and isn't after the equal sign
	 */
	public String[] getMetaData();
	public boolean equalsMeta(ILineMeta other);

}
