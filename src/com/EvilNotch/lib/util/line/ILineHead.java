package com.EvilNotch.lib.util.line;

import java.util.List;

public interface ILineHead extends ILine{
	
	public List<Object> getHeads();
	public Object getHeadAtIndex(int index);
	
	/*public int getInt(int index);
	public short getShort(int index);
	public long getLong(int index);
	public byte getByte(int index);
	public float getFloat(int index);
	public double getDouble(int index);
	public String getString(int index);
	public boolean getBoolean(int index);
	
	public int getInt(ArrEntry head,int index);
	public short getShort(ArrEntry head,int index);
	public long getLong(ArrEntry head,int index);
	public byte getByte(ArrEntry head,int index);
	public float getFloat(ArrEntry head,int index);
	public double getDouble(ArrEntry head,int index);
	public String getString(ArrEntry head,int index);
	public boolean getBoolean(ArrEntry head,int index);*/
	
}
