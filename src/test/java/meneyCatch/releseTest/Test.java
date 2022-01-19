package meneyCatch.releseTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.cc.cache.CcCache;


public class Test {
	
	public static void main(String[] args) throws Exception {
		
		CcCache ccCache = new CcCache(null);
		List<TestThread> list = new ArrayList<TestThread>();
		String name = "test";
		
		for(int j=0;j<5;j++) {
			list.add(new TestThread(ccCache,name));
		}
		
		CyclicBarrier cyclicBarrier = new CyclicBarrier(list.size()+1);
		TestThread.setCyclicBarrier(cyclicBarrier);
		
		for(TestThread tt: list) {
			tt.start();
		}
		
		try {
			cyclicBarrier.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("test= "+ccCache.get(name));
		
		Thread.sleep(5000);
		
		for(Entry<String, Integer> ele :  ccCache.getCatcheState().entrySet()) {
			System.out.println(ele.getKey()+" : " + ele.getValue());
		}
		
		System.exit(0);
		
	}
	
}
