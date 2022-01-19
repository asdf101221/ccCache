package meneyCatch;

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
	    	for(int i=0;i<10000;i++) {
	    		name = name + i;
				Integer valueTemp = 1;
				catche.put(name,valueTemp,1);
				//System.out.println("valueTemp: " + valueTemp);
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
