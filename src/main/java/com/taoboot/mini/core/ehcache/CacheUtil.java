package com.taoboot.mini.core.ehcache;

import com.taoboot.mini.core.constant.CacheConstant;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ehcache 缓存
 *
 * @author chentao
 * @create 2019/6/3
 * @since 1.0.0
 */
@Component
public class CacheUtil {

    private static final class LazyLoader{
        private static  final CacheUtil INSTANCE = new CacheUtil();
    }

    public static CacheUtil getInstance(){
        return LazyLoader.INSTANCE;
    }

    private CacheManager cacheManager = null;
    private Map<String, Cache> map = new ConcurrentHashMap<>();

    public CacheUtil(){
        cacheManager = CacheManager.getInstance();
    }

    private Cache initCache(String group){
        Cache cache = map.get(group);
        if(cache == null){
            cache =cacheManager.getCache(group);
            map.put(group, cache);
        }
        if(cache == null){
            cacheManager.addCache(group);
            cache = cacheManager.getCache(group);
            map.put(group, cache);
        }
        return cache;
    }

    public Object get(String group, Object key){
        Cache cache = initCache(group);
        Object obj = null;
        if(key != null){
            Element element = cache.get(key);
            if(element != null)
                obj = element.getObjectValue();
        }
        return obj;
    }

    /**
     * 加入缓存
     * @param group
     * @param key
     * @param value
     */
    public void put(String group, Object key, Object value) {
        Cache cache = initCache(group);
        try {
            Element element = new Element((Serializable) key,
                    (Serializable) value);
            cache.put(element);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加入缓存带失效时间
     * @param group
     * @param key
     * @param value
     * @param exp
     */
    public void put(String group, Object key, Object value, int exp) {
        Cache cache = initCache(group);
        try {
            Element element = new Element((Serializable) key,
                    (Serializable) value);
            element.setTimeToLive(exp);
            cache.put(element);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除缓存
     * @param group
     * @param key
     */
    public void remove(String group, Object key) {
        Cache cache = initCache(group);
        try {
            cache.remove((Serializable) key);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空缓存
     * @param group
     */
    public void clear(String group) {
        Cache cache = initCache(group);
        try {
            //cache.remove(arg0)
            cache.removeAll();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CacheUtil cacheUtil = CacheUtil.getInstance();
        cacheUtil.put(CacheConstant.WXOPENID.getCode(), "123", "123");

        //System.out.println(cacheUtil.get(CacheConstant.WXOPENID.getCode(), "123"));
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cacheUtil.get(CacheConstant.WXOPENID.getCode(), "123"));
    }

}