package com.cc.cache;

import java.util.Map;

/***
 * @author cheng chen
 * @Date 20210913
 * @summary 
 * ***/
public interface ICatch {
	
	/******/
	public Object put(String key,Object value);
	
	/***
	 * 
	 * @param key
	 * @param value
	 * @time expired time (Unit s)
	 * ***/
	public Object put(String key,Object value,int time);
	
	/***
	 * @summary use key take value;
	 * @param key
	 * 
	 * **/
	public Object get(String key);
	
	/***
	 * @summary use key take value;
	 * @param key
	 * 
	 * **/
	public <T> T get(String key,Class<T> classObj);
	
	/***
	 * @summary remove one object for cache
	 * @param key 
	 * 
	 * ****/
	public Object remove(String key);
	
	
	/****
	 * @summary remove all cache object
	 * ****/
	public void removeALL();
	
	/***@summary clear all expired object
	 * 
	 * ***/
	public Object cleanExpired();
	
	/***
	 * @summary return all catche state
	 * *****/
	public Map<String,Integer> getCatcheState();
	
}
