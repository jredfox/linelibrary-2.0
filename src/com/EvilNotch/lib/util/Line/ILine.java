package com.EvilNotch.lib.util.Line;

import net.minecraft.util.ResourceLocation;

public interface ILine extends Comparable<ILine>{
	
	public static final String version = "2.0";
	/**
	 * this is the identifier of the line
	 */
	public String getId();
	/**
	 * return the lines string you want it to be sorted by
	 */
	public String getComparible();
	
	/**
	 * turn the line into a resource location
	 */
	public default ResourceLocation getResourceLocation(){
		return new ResourceLocation(getId());
	}
	/**
	 * for sorting the lines alphabetically and stuff
	 */
	public default int compareTo(ILine o) 
	{
		return this.getComparible().compareTo(o.getComparible());
	}

}
