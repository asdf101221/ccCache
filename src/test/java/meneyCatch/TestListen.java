package meneyCatch;

import java.util.HashMap;
import java.util.Map;

import com.cc.cache.CcCache;
import com.cc.cache.listen.ICcCacheListen;

public class TestListen implements ICcCacheListen{
	
	public void action(Object object) {
		System.out.println(object);
		
	}
	
	public static void main(String[] args) throws Exception {
		Map<String,ICcCacheListen> listenMap = new HashMap<String,ICcCacheListen>();
		listenMap.put(CcCache.EXPIRED_LISTEN,new TestListen());
		listenMap.put(CcCache.REMOVE_LISTEN,new TestListen());
		CcCache catche = new CcCache(listenMap);
		catche.put("123", "2222",4);
		catche.put("3333", "22",4);
		catche.remove("3333");
		for(int i=0;i<4;i++) {
			Thread.sleep(1000);
			System.out.println(catche.get("123"));
		}
		
	}
	
}
