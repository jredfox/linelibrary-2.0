package com.EvilNotch.lib.util.line;

import java.util.List;

public interface ILineHead extends ILine{
	
	public List<Object> getHeads();
	public Object getHeadAtIndex(int index);
	public int size();
	
	/**
	 * for objects that only support one head you should use this
	 */
	public default Object getHead(){
		return this.getHeadAtIndex(0);
	}
	/*public int getInt(int index);
	public short getShort(int index);
	public long getLong(int index);
	public byte getByte(int index);
	public float getFloat(int index);
	public double getDouble(int index);
	public String getString(int index);
	public boolean getBoolean(int index);
   */
	
}
