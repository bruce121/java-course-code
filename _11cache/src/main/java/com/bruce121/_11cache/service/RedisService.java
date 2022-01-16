package com.bruce121._11cache.service;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: RedisService
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 2022/1/16 10:22 PM
 * @Version 1.0
 */
@Service
public class RedisService {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 自增1
     *
     * @param key key
     * @return long
     */
    public long incr(String key) {

        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        return atomicLong.incrementAndGet();
    }

    /**
     * 自减1
     *
     * @param key key
     * @return long
     */
    public long decr(String key) {

        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        return atomicLong.decrementAndGet();
    }
}
