package org.cloudcompaas.common.security.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author angarg12
 *
 */
public class UserCache {
	private static Map<String,String> cache = new HashMap<String,String>();
	private static String platformUsername;

	public static void bootstrapping(){
		try{
			Properties properties = new Properties();
			properties.load(UserCache.class.getResourceAsStream("/conf/credentials.properties"));
			platformUsername = properties.getProperty("username");
			String basicToken = properties.getProperty("basictoken");
			put(platformUsername,basicToken);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public synchronized static void put(String username, String basicToken){
		cache.put(username, basicToken);
	}
	
	public synchronized static String get(String username){
		return cache.get(username);
	}
	
	public synchronized static String getUsername(String basicToken){
		Set<String> keys = cache.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String key = it.next();
			if(cache.get(key).equals(basicToken)){
				return key;
			}
		}
		return null;
	}
	
	public synchronized static String getPlatformToken(){
		return cache.get(platformUsername);
	}
}
