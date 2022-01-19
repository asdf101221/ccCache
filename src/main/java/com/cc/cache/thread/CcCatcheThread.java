package com.cc.cache.thread;

import com.cc.cache.ICatch;
/***
 * @author chengchen
 * @summary clear data thread
 * ******/
public class CcCatcheThread extends Thread {
	
	
	ICatch iCatch = null;
	boolean flag = true;
	 class ShutdownCallbackThread extends Thread{
         public void run(){
        	 flag=false;
         }//退出钩子
     }
	
	@Override
	public void run() {
		ShutdownCallbackThread hook = new ShutdownCallbackThread();
		Runtime.getRuntime().addShutdownHook(hook);
		while(flag) {
			try {
				this.iCatch.cleanExpired();
			}catch(Exception e) {}
		}
	}
	
	public CcCatcheThread(ICatch iCatch ) {
		this.iCatch = iCatch;
		
	}
	
	
}
