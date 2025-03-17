package com.xiaoxipeng.api.service.impl;

import com.xiaoxipeng.api.service.IBloomService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.xiaoxipeng.api.constant.Global.MY_BLOOM_FILTER;

@Component
@Slf4j
public class BloomServiceImpl implements IBloomService {


    private final RedissonClient redissonClient;

    @Autowired
    public BloomServiceImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void createBloom() {
        // 创建Bloom过滤器，预计插入1000个元素
        RBloomFilter<String> myBloomFilter = redissonClient.<String>getBloomFilter(MY_BLOOM_FILTER);
        myBloomFilter.tryInit(100000, 0.01);
    }

    @Override
    public void testBloom() {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(MY_BLOOM_FILTER);
        for (int i = 100000; i < 160000; i++) {
            bloomFilter.add(i);
        }
    }

    @Override
    public void testContain() {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(MY_BLOOM_FILTER);
        for (int i = 200000; i < 260000; i++) {
            log.info("{}是否存在 ===> {}", i, bloomFilter.contains(i));
        }
    }
}
