package meneyCatch.sysTest;


import java.util.Map.Entry;

import com.cc.cache.CcCache;


public class Test {
	
	public static void main(String[] args) throws Exception {
		CcCache catche = new CcCache();
		Integer cvalue = 0;
		String name = "test";
		for(int j=0;j<2;j++) {
			String nameTemp =  "test"+j;
			for(int i=0;i<100;i++) {
				//System.out.println("value=" + i);
				//Thread.sleep(999);
				catche.put(nameTemp, i,1);
				cvalue =  (Integer)catche.get(nameTemp);
				
				if(cvalue == null || cvalue.intValue() != i) {
					System.out.println("err catch value=" + cvalue);
				}
				System.out.println("catch value=" + catche.get(nameTemp));
				
			}
		}
		Thread.sleep(5000);
		for(Entry<String, Integer> ele :  catche.getCatcheState().entrySet()) {
			System.out.println(ele.getKey()+" : " + ele.getValue());
		}
		System.exit(0);
		
	}
	
}
