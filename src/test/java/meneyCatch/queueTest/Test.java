package meneyCatch.queueTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;

import com.cc.cache.data.CcCatcheMenu;

public class Test {
	
	public static void main(String[] args) throws InterruptedException {
		DelayQueue<CcCatcheMenu> delayQueue =new DelayQueue<CcCatcheMenu>();
		CcCatcheMenu ccCatcheMenu;
		
		for(int i=1;i<10;i++) {
			delayQueue.put(new CcCatcheMenu("test",i));
		}
		
		while(delayQueue.size()>0) {
			ccCatcheMenu = delayQueue.poll();
			if(ccCatcheMenu!=null) {
				System.out.println("have value ccCatcheMenu : "+ ccCatcheMenu.getKey() + ccCatcheMenu.getDate());
			}else {
				System.out.println("ccCatcheMenu : "+ null);
				Thread.sleep(500);
			}
		}
		
		
		
	}
	
}
