package com.EvilNotch.lib.util.line;

import java.util.List;

public interface ILineHeadArray extends ILineHead{
	
	public List<Object> getHeads();
	public Object getHeadAtIndex(int index);
	public void setObject(Object obj,int index);
	public int size();
	
	/**
	 * for people trying to use this as ILineHead instead
	 */
	@Override
	public default void setHead(Object obj){
		this.setObject(obj, 0);
	}
	/**
	 * for people trying to use this as ILineHead instead
	 */
	@Override
	public default Object getHead(){
		return this.getHeadAtIndex(0);
	}
}
