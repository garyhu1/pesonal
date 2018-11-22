package com.garyhu.conf;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;

/**
 * @author: garyhu
 * @since: 2018/11/22 0022
 * @decription:
 */
public class TwoLevelCacheManager extends RedisCacheManager {

    private RedisTemplate redisTemplate;

    public TwoLevelCacheManager(RedisTemplate redisTemplate,RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Cache decorateCache(Cache cache) {
        return super.decorateCache(cache);
    }

    public void publishMessage(String cacheName){
        this.redisTemplate.convertAndSend("refresh",cacheName);
    }

    // 接收一个消息来清空本地缓存
    public void receive(String name){
        RedisAndLocalCache cache = (RedisAndLocalCache) this.getCache(name);
        if(cache != null){
            cache.clearLocal();
        }
    }
}
