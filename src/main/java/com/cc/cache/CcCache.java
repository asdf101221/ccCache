package com.cc.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.cc.cache.listen.ICcCacheListen;
import com.cc.cache.thread.CcCatcheThread;

/***
 * @author chengchen
 * @date 20210915
 * @summary catche manage class
 * ***/
public class CcCache implements ICatch{
	
	/***expired even***/
	public final static String EXPIRED_LISTEN = "EXPIRED_LISTEN";
	
	/***remove even***/
	public final static String REMOVE_LISTEN = "REMOVE_LISTEN";
	
	
	
	
	private CcCatcheThread ccCatcheThread = null;
	private Map <String,ICcCacheListen> listenMap;
	
	private CcMap ccMap = new CcMap();
	
	public CcCache() {
		this(null);
	}
	
	public CcCache(Map<String,ICcCacheListen> listenMap) {
		if(listenMap==null) {
			listenMap = new HashMap<String,ICcCacheListen>();
		}
		this.listenMap = listenMap;
		ccCatcheThread = new CcCatcheThread(this);
		ccCatcheThread.setDaemon(true);
		ccCatcheThread.start();
	}
	
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary put the object to fix cache
	 * @param key data key
	 * @param value cache data
	 * @return value
	 * ****/
	public Object put(String key, Object value) {
		return put(key,value,-1);
		
	}
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary put the object to cache and set expiration time
	 * @param key key
	 * @param value cache object
	 * @time time unit second 
	 * @return value
	 * ****/
	public Object get(String key) {
		
		return ccMap.get(key);
	}
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary return valid data in catch 
	 * @param key key
	 * */
	public <T> T get(String key, Class<T> classObj) {
		
		return ccMap.get(key,classObj);
	}
	
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary remove  expired data in catch 
	 * */
	public Object cleanExpired() {
		Object value = ccMap.cleanExpired();
		Map<String, Object> map = (Map<String, Object> )value;
		
		if(listenMap.get(CcCache.EXPIRED_LISTEN)!=null) {
			if(map!=null) {
				for(Entry<String, Object> entry:map.entrySet()) {
					listenMap.get(CcCache.EXPIRED_LISTEN).action(entry.getValue());
				}
			}
		}
		return value;
	}

	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary put the object to cache and set expiration time
	 * @param key key
	 * @param value cache object
	 * @time time unit second 
	 * @return value
	 * ****/
	public Object put(String key, Object value, int time) {
		
		return ccMap.put(key, value,time);
		
	}
	
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary  do put remove expired get job
	 * @param catcheMenu menu data
	 * @return remove data 
	 * */
	public Object remove(String key) {
		Object value = ccMap.remove(key);
		if(listenMap.get(CcCache.REMOVE_LISTEN)!=null) {
			listenMap.get(CcCache.REMOVE_LISTEN).action(value);
		}
		return value;
	}
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary remove key value in catch 
	 * @param key key
	 * */
	public void removeALL() {
		ccMap.removeALL();
	}

	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary  return all catch size
	 * @return all catch size
	 * */
	public Map<String, Integer> getCatcheState() {
		return ccMap.getCatcheState();
	}
	
	
	
}
