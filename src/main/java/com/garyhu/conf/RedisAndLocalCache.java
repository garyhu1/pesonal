package com.garyhu.conf;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: garyhu
 * @since: 2018/11/22 0022
 * @decription:
 */
public class RedisAndLocalCache implements Cache {

    // 本地缓存提供
    ConcurrentHashMap<Object,Object> local = new ConcurrentHashMap<>();

    RedisCache redisCache;

    TwoLevelCacheManager cacheManager;

    public RedisAndLocalCache(TwoLevelCacheManager cacheManager,RedisCache redisCache){
        this.cacheManager = cacheManager;
        this.redisCache = redisCache;
    }

    @Override
    public String getName() {
        return redisCache.getName();
    }

    @Override
    public Object getNativeCache() {
        return redisCache.getNativeCache();
    }

    @Override
    public ValueWrapper get(Object key) {
        // 先取一级缓存
        ValueWrapper wrapper = (ValueWrapper) local.get(key);
        if(wrapper != null){
            return wrapper;
        }else {
            // 从二级缓存中取
            wrapper = redisCache.get(key);
            if(wrapper != null){
                local.put(key,wrapper);
            }
            return wrapper;
        }
    }

    @Override
    public <T> T get(Object o, Class<T> aClass) {
        return null;
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        System.out.println(value.getClass().getClassLoader());
        redisCache.put(key,value);
        // 通知其他节点缓存更新
        clearOtherJVM();
    }

    @Override
    public ValueWrapper putIfAbsent(Object o, Object o1) {
        return null;
    }

    @Override
    public void evict(Object key) {
        redisCache.evict(key);
        // 通知其他节点更新
        clearOtherJVM();
    }

    @Override
    public void clear() {

    }

    /**
     * 发消息，通知更新缓存
     */
    public void clearOtherJVM(){
        cacheManager.publishMessage(redisCache.getName());
    }

    /**
     * 清空一级缓存
     */
    public void clearLocal(){
        this.local.clear();
    }
}
