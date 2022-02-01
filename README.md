# ccCache
Caches designed for communication and auditing JAVA memory cache  
Summary
When you need to fast release of object in catch. You can use this cache.
specialty list:
1、When the cache expired ,we will clean it now.
2、Support for remove and expired events

How to build the project?
run 
mvn clean package
command

How to used the project?
You can uased maven add dependency.
</br>&lt;dependency&gt; &nbsp;
</br>&lt;groupId&gt;io.github.asdf101221&lt;/groupId&gt; &nbsp;
</br>&lt;artifactId&gt;cc_cache&lt;/artifactId&gt; 
</br>&nbsp;&lt;version&gt;1.0.1-RELEASE&lt;/version&gt; 
</br>&lt;/dependency&gt;

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

中文说明：
为通信和审计设计的缓存
   因为，市面上大部份缓存都是针对页面设计的。但对于很多系统来说这些缓存在一些问题。
   一 当缓存过期后，不会删除，只会在缓存满时以队列方式把最早的缓存删除。
   二 缓存不支持过期事件。
   三 功能过多导至性能慢。

因此，我开了发这个缓存，希望可以解决上述问题。

性能实测：
10万条不同KEY的记录写入大概需要0.2秒

如何使用
maven 引入</br>
</br>&lt;dependency&gt; &nbsp;
</br>&lt;groupId&gt;io.github.asdf101221&lt;/groupId&gt; &nbsp;
</br>&lt;artifactId&gt;cc_cache&lt;/artifactId&gt; 
</br>&nbsp;&lt;version&gt;1.0.1-RELEASE&lt;/version&gt; 
</br>&lt;/dependency&gt;

//Create object
CcCache ccCache = new CcCache(null);
//Put the object 
//test is key, value is 123456 , expire time 1 second
catche.put("test","123456",1);
//If you don't fill expire time.That mean it don't expire
catche.put("test","123456");

如何使用事件?
//你需要实现一下 ICcCatcheListen 接口
public class TestListen implements ICcCacheListen{
	
	public void action(Object object) {
		System.out.println(object);
		
	}
}

//建一个map 把事件放进去。
Map<String,ICcCatcheListen> listenMap = new HashMap<String,ICcCatcheListen>();
listenMap.put(CcCache.EXPIRED_LISTEN,new TestListen());//CcCache.EXPIRED_LISTEN 过期事件
listenMap.put(CcCache.REMOVE_LISTEN,new TestListen());//CcCache.REMOVE_LISTEN 删除事件
CcCache ccCache = new CcCache(listenMap);

