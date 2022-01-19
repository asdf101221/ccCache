# ccCache
With timing, true deletion, support for a large number of events, using a simple JAVA memory cache  
Summary
When you need to fast release of object in catch. You can use this cache.
specialty list:
1、When the cache expired ,we will clean it now.
2、Sup

How to build the project?
run 
mvn clean package
command

How to used the project?
//Create object
CcCache ccCache = new CcCache(null);
//Put the object 
//test is key, value is 123456 , expire time 1 second
catche.put("test","123456",1);
//If you don't fill expire time.That mean it don't expire
catche.put("test","123456");

How use listen?
//You need to implements ICcCatcheListen 
public class TestListen implements ICcCacheListen{
	
	public void action(Object object) {
		System.out.println(object);
		
	}
}

Map<String,ICcCatcheListen> listenMap = new HashMap<String,ICcCatcheListen>();
listenMap.put(CcCache.EXPIRED_LISTEN,new TestListen());//CcCache.EXPIRED_LISTEN expired even
listenMap.put(CcCache.REMOVE_LISTEN,new TestListen());//CcCache.REMOVE_LISTEN expired even
CcCache ccCache = new CcCache(listenMap);

