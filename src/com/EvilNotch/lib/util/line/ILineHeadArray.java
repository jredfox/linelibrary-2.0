package com.EvilNotch.lib.util.line;

import java.util.List;
/**
 * used in LineArray for multi index heads
 * @author jredfox
 *
 */
public interface ILineHeadArray extends ILineHead{
	
	public List<Object> getHeads();
	public Object getHead(int index);
	public void setHead(Object obj,int index);
	public int size();
	
	/**
	 * for people trying to use this as ILineHead instead
	 */
	@Override
	public default void setHead(Object obj){
		this.setHead(obj, 0);
	}
	/**
	 * for people trying to use this as ILineHead instead
	 */
	@Override
	public default Object getHead(){
		return this.getHead(0);
	}
}
