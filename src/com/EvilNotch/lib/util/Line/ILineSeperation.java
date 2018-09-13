package com.EvilNotch.lib.util.Line;

public interface ILineSeperation extends ILine{
	/**
	 * May be null
	 * @return An array of data that isn't the identifier and isn't after the equal sign
	 */
	public String[] getMetaData();
	
	public char getSeprator();

}
