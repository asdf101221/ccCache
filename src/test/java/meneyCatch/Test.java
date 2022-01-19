package meneyCatch;


import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CyclicBarrier;

import com.cc.cache.CcCache;

public class Test {
	
	public static void main(String[] args) throws Exception {
		
		CcCache cache = new CcCache(null);
		List<TestThread> list = new ArrayList<TestThread>();
		//System.out.println(System.currentTimeMillis());
		
		for(int j=0;j<10;j++) {
			list.add(new TestThread(cache,"test"+j));
		}
		
		CyclicBarrier cyclicBarrier = new CyclicBarrier(list.size());
		TestThread.setCyclicBarrier(cyclicBarrier);
		long start = System.currentTimeMillis();
		for(TestThread tt: list) {
			tt.start();
		}
		
//		try {
//			//cyclicBarrier.await();
//			for(int i=0;i<100000;i++) {
//				catche.put("abc"+i, "aaa",1);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		System.out.println("用时："+(System.currentTimeMillis()-start));
		
		Thread.sleep(5000);
		
		for(Entry<String, Integer> ele :  cache.getCatcheState().entrySet()) {
			System.out.println(ele.getKey()+" : " + ele.getValue());
		}
		System.exit(0);

		
	}
	
}
