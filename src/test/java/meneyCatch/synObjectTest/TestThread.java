package meneyCatch.synObjectTest;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.cc.cache.CcCache;



public class TestThread extends Thread{
	CcCache catche = null;
	String name = null;
	
	static CyclicBarrier cyclicBarrier;
	
	public static void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
		TestThread.cyclicBarrier = cyclicBarrier;
	}
	
	public TestThread(CcCache catche,String name) {
		this.catche = catche;
		this.name = name;
	}

	@Override
	public void run() {	
		TestThread.doAction(name,catche);
	}
	
	public static void doAction(String name,CcCache catche) {
		synchronized (name) {
	    	for(int i=0;i<10;i++) {
				Integer valueTemp = catche.get(name,Integer.class);
				if(valueTemp !=null) {
					valueTemp++;
				}else {
					valueTemp = 1;
				}
				catche.put(name,valueTemp);
				System.out.println("valueTemp: " + valueTemp);
			}
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
	}
	
}
