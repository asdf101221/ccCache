package com.cc.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicLong;

import com.cc.cache.data.CcCatcheMenu;
import com.cc.cache.enums.OperationType;

/***@author chengchen
 * @Date 20210915
 * @summary catche data map
 * ****/
public class CcMap implements ICatch{
	
	private AtomicLong expiredTime =new AtomicLong();
	
	
	private AtomicLong putTime = new AtomicLong();
	
	//menuMap
	private Map<String, CcCatcheMenu> menuMap =  new ConcurrentHashMap<String, CcCatcheMenu>(102400);
	
	//data map had expiration time
	private Map<Long, Map<String,Object>> dateMap = new ConcurrentHashMap<Long, Map<String,Object>>(102400);
	
	//fix data map
     private Map<String,Object> finalMap = new ConcurrentHashMap<String,Object>(10240);
	
	//expired queue
	private DelayQueue<CcCatcheMenu> delayQueue =new DelayQueue<CcCatcheMenu>();
	
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary put the object to fix cache
	 * @param key data key
	 * @param value cache data
	 * @return value
	 * ****/
	public Object put(String key, Object value) {
		return put(key, value, -1); 
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
		putTime.getAndIncrement();
		return doJob(key,value,time,OperationType.put);
	}
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary return valid data in catch 
	 * @param key key
	 * */
	public Object get(String key) {
		
		return doJob(key,OperationType.get);
	}
	
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary return valid data in catch 
	 * @param key key
	 * @param classObj return object class type
	 * */
	public <T> T get(String key, Class<T> classObj) {
		T t = (T)this.get(key);
		return t;
	}
	
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary remove key value in catch 
	 * @param key key
	 * @return value remove value
	 * */
	public Object remove(String key) {
		return doJob(key,OperationType.remove);
	}
	
	

	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary remove key value in catch 
	 * @param key key
	 * */
	public void removeALL() {
		synchronized (this) {
			menuMap.clear();
			dateMap.clear();
			delayQueue.clear();
		} 
		
	}
	

	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary remove  expired data in catch 
	 * */
	public Object cleanExpired(){
		try {
			CcCatcheMenu catcheMenu = null;
			catcheMenu = delayQueue.poll();
			if(catcheMenu!=null) {
				expiredTime.getAndIncrement();
				return doJob(catcheMenu.getKey(),OperationType.expried,catcheMenu);
			}else {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary  do put remove expired get job
	 * @param key data key
	 * @param OperationType operation type
	 * */
	public Object doJob(String key,Object value,int time, OperationType type) {
		return doJob(key,value,time,type,null);
	}
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary  do put remove expired get job
	 * @param key data key
	 * @param OperationType operation type
	 * */
	public Object doJob(String key,OperationType type,CcCatcheMenu catcheMenu) {
		return doJob(key,null,0,type,catcheMenu);
	}
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary  do put remove expired get job
	 * @param key data key
	 * @param OperationType operation type
	 * */
	public Object doJob(String key,OperationType type) {
		return doJob(key,null,-1,type,null);
	}
	
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary  do put remove expired get job
	 * @param key data key
	 * @param value data 
	 * @param OperationType operation type
	 * */
	public Object doJob(String key,Object value,OperationType type) {
		return doJob(key,value,-1,type,null);
	}
	
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary  do put remove expired get job
	 * @param key data key
	 * @param value data
	 * @param time expired time unit second
	 * @param OperationType operation type
	 * */
	public Object doJob(String key,Object value,int time,OperationType type,CcCatcheMenu catcheMenuParam) {
		
		CcCatcheMenu catcheMenu;
		if(catcheMenuParam!=null) {
			catcheMenu = catcheMenuParam;
		}else {
			 catcheMenu = menuMap.get(key);
		}
		if(catcheMenu != null) {
			key = catcheMenu.getKey();
		}
		synchronized (key) {
			if(catcheMenu == null) {
				catcheMenu = new CcCatcheMenu(key, time);
			}
			switch (type) {
				case get:
					if(catcheMenu.getDate()<1) {
						value = finalMap.get(key);
					}
					Map<String,Object> mapTemp = dateMap.get(catcheMenu.getDate());
					if(mapTemp != null) {
						value = mapTemp.get(key);
					}
					break;
				
				case put:
					
					if(time < 1) {
						//***fix object
						catcheMenu = new CcCatcheMenu(catcheMenu.getKey(),time);
						menuMap.put(catcheMenu.getKey(), catcheMenu);
						finalMap.put(catcheMenu.getKey(), value);
					}else {
						catcheMenu = new CcCatcheMenu(catcheMenu.getKey(),time);
						menuMap.put(catcheMenu.getKey(), catcheMenu);
						
						Map<String,Object> map = dateMap.get(catcheMenu.getDate());
						if(map == null) {
							map = new HashMap<String,Object>();
							dateMap.put(catcheMenu.getDate(), map);
						}
						//System.out.println("put expired catcheMenu="+catcheMenu.getKey()+":"+catcheMenu.getDate());
						map.put(key, value);
						delayQueue.put(catcheMenu);
					}
					
					break;
					
				case remove:
					value = remove(catcheMenu);
					break;
				case expried:
					value = dateMap.remove(catcheMenu.getDate());
					CcCatcheMenu catcheMenuTemp = menuMap.get(catcheMenu.getKey());
					if(catcheMenuTemp != null && catcheMenuTemp.getDate() == catcheMenu.getDate()) {
						menuMap.remove(catcheMenu.getKey());
					}
					break;
			}
		}
		return value;
		
	}
	
	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary  do put remove expired get job
	 * @param catcheMenu menu data
	 * @return remove data 
	 * */
	private Object remove(CcCatcheMenu catcheMenu) {
		menuMap.remove(catcheMenu.getKey());
		finalMap.remove(catcheMenu.getKey());
		Map<String, Object> mapTemp1 = dateMap.get(catcheMenu.getDate());
		Object valueTemp=null;
		if(mapTemp1!=null) {
			valueTemp = mapTemp1.get(catcheMenu.getKey());
			mapTemp1.remove(catcheMenu.getKey());
		}
		
		return valueTemp;
	}

	/****
	 * @author chengchen
	 * @Date 20210915
	 * @summary  return all catch size
	 * @return all catch size
	 * */
	public Map<String, Integer> getCatcheState() {
		Map<String, Integer> map = new HashMap<String, Integer> ();
		map.put("size:menuMap",menuMap.size());
		map.put("size:dateMap",dateMap.size());
		map.put("size:finalMap",finalMap.size());
		map.put("size:delayQueue",delayQueue.size());
		map.put("hit_time:putTime",putTime.intValue());
		map.put("hit_time:expiredTime",expiredTime.intValue());
		return map;
	}
	
	
}
