package com.cc.cache.data;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/***@author chencheng
 * @Date 20210913
 * @summary menu object 
 * **/
public class CcCatcheMenu implements Delayed{
	
	/***expired time**/
	private long date=0;
	
	/***cache key**/
	private String key;
	
	public CcCatcheMenu(String key,int time) {
		if(time>0) {
			this.date = System.currentTimeMillis() + (1000*time);
		}
		this.key = key;
	}
	
	public long getDate() {
		return date;
	}
	
	public void setDate(long date) {
		this.date = date;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	
	
	@Override
	public boolean equals(Object paramObject) {
		if(paramObject instanceof CcCatcheMenu) {
			CcCatcheMenu ccCatcheMenu = (CcCatcheMenu)paramObject;
			return this.getKey().equals(ccCatcheMenu.getKey()) && this.getDate() == ccCatcheMenu.getDate();
		}
		
		return false;
	}

	public int compareTo(Delayed other) {
		long diff = this.getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS);
        return diff == 0 ? 0 : diff>0 ? 1:-1;
	}
	
	public long getDelay(TimeUnit unit) {
		return date - System.currentTimeMillis();
	}
	
}
