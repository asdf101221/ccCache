package meneyCatch.synObjectTest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.cc.cache.CcCache;


public class Test {
	
	public static void main(String[] args) {
		CcCache cache = new CcCache(null);
		List<TestThread> list = new ArrayList<TestThread>();
		for(int j=0;j<2;j++) {
			list.add(new TestThread(cache,"test"));
			
		}
		
		CyclicBarrier c = new CyclicBarrier(list.size()+1);
		TestThread.setCyclicBarrier(c);
		
		for(TestThread tt: list) {
			tt.start();
		}
		
		try {
			c.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test= "+cache.get("test"));
		System.exit(0);
		
	}
	
}
