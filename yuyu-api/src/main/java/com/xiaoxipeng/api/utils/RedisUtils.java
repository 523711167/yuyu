package com.xiaoxipeng.api.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

    private final RedissonClient redissonClient;

    @Autowired
    public RedisUtils(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public Boolean tryLock(String key) {
        return null;
    }

}
