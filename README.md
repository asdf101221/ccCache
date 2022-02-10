# ccCache
Caches designed for communication and auditing JAVA memory cache  </br>
Summary</br>
When you need to fast release of object in catch. You can use this cache.</br>
specialty list:</br>
1、When the cache expired ,we will clean it now.</br>
2、Support for remove and expired events</br>
</br>
How to build the project?</br>
run </br>
mvn clean package</br>
command</br>

How to used the project?</br>
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
//You need to implements ICcCatcheListen </br>
public class TestListen implements ICcCacheListen{</br>
	</br>
	public void action(Object object) {</br>
		System.out.println(object);</br>
		</br>
	}</br>
}</br>

Map<String,ICcCacheListen> listenMap = new HashMap<String,ICcCacheListen>();</br>
listenMap.put(CcCache.EXPIRED_LISTEN,new TestListen());//CcCache.EXPIRED_LISTEN expired event</br>
listenMap.put(CcCache.REMOVE_LISTEN,new TestListen());//CcCache.REMOVE_LISTEN remove event</br>
CcCache catche = new CcCache(listenMap);</br>

</br>
Complete sample</br>
public class TestListen implements ICcCacheListen{</br>
	</br>
	public void action(Object object) {</br>
		System.out.println(object);</br>
		</br>
	}</br>
	
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

中文说明：
为通信和审计设计的缓存
   因为，市面上大部份缓存都是针对页面设计的。但对于很多系统来说这些缓存在一些问题。</br>
   一 当缓存过期后，不会删除，只会在缓存满时以队列方式把最早的缓存删除。</br>
   二 缓存不支持过期事件。</br>
   三 功能过多导至性能慢。</br>
</br>
因此，我开了发这个缓存，希望可以解决上述问题。</br>
</br>
性能实测：</br>
10万条不同KEY的记录写入大概需要0.2秒</br>
相同条件下oscache需时2秒左右</br>

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
//你需要实现一下 ICcCacheListen 接口
public class TestListen implements ICcCacheListen{</br>
	</br>
	public void action(Object object) {</br>
		System.out.println(object);</br>
		</br>
	}</br>
}</br>

//建一个map 把事件放进去。</br>
Map<String,ICcCacheListen> listenMap = new HashMap<String,ICcCacheListen>();</br>
listenMap.put(CcCache.EXPIRED_LISTEN,new TestListen());//CcCache.EXPIRED_LISTEN 过期事件</br>
listenMap.put(CcCache.REMOVE_LISTEN,new TestListen());//CcCache.REMOVE_LISTEN 删除事件</br>
CcCache catche = new CcCache(listenMap);</br>

</br>
完整示例</br>
public class TestListen implements ICcCacheListen{

	public void action(Object object) {
		System.out.println(object);

	}
	
	public static void main(String[] args) throws Exception {
		Map<String,ICcCacheListen> listenMap = new HashMap<String,ICcCacheListen>();
		listenMap.put(CcCache.EXPIRED_LISTEN,new TestListen());
		listenMap.put(CcCache.REMOVE_LISTEN,new TestListen());
		CcCache catche = new CcCache(listenMap);
		catche.put("123", "2222",4);</br>
		catche.put("3333", "22",4);</br>
		catche.remove("3333");</br>
		for(int i=0;i<4;i++) {</br>
			Thread.sleep(1000);
			System.out.println(catche.get("123"));
		}
		
	}
	
}
