package com.xiaoxipeng.api.service.impl;

import com.xiaoxipeng.api.service.IRedisService;
import com.xiaoxipeng.exception.YuyuException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.xiaoxipeng.auth.constant.Global.*;


@Component
@Slf4j
public class RedisServiceImpl implements IRedisService {

    String msg = "{\n" +
            "  \"order_id\": \"ORD123456\",\n" +
            "  \"customer\": {\n" +
            "    \"customer_id\": \"CUST7890\",\n" +
            "    \"name\": \"张三\",\n" +
            "    \"email\": \"zhangsan@example.com\",\n" +
            "    \"phone\": \"13800000000\",\n" +
            "    \"address\": {\n" +
            "      \"street\": \"北京市朝阳区某某街道\",\n" +
            "      \"city\": \"北京\",\n" +
            "      \"province\": \"北京\",\n" +
            "      \"postal_code\": \"100000\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"order_date\": \"%s\",\n" +
            "  \"shipping_date\": \"2023-10-17T10:00:00Z\",\n" +
            "  \"status\": \"shipped\",\n" +
            "  \"items\": [\n" +
            "    {\n" +
            "      \"item_id\": \"ITEM001\",\n" +
            "      \"name\": \"蓝牙耳机\",\n" +
            "      \"quantity\": 2,\n" +
            "      \"price\": 199.99,\n" +
            "      \"subtotal\": 399.98\n" +
            "    },\n" +
            "    {\n" +
            "      \"item_id\": \"ITEM002\",\n" +
            "      \"name\": \"无线鼠标\",\n" +
            "      \"quantity\": 1,\n" +
            "      \"price\": 99.99,\n" +
            "      \"subtotal\": 99.99\n" +
            "    }\n" +
            "  ],\n" +
            "  \"total_amount\": 499.97,\n" +
            "  \"payment\": {\n" +
            "    \"payment_method\": \"信用卡\",\n" +
            "    \"payment_status\": \"已付款\",\n" +
            "    \"transaction_id\": \"TRANS456789\"\n" +
            "  },\n" +
            "  \"shipping\": {\n" +
            "    \"carrier\": \"顺丰快递\",\n" +
            "    \"tracking_number\": \"SF123456789\",\n" +
            "    \"shipping_cost\": 20.00\n" +
            "  },\n" +
            "  \"notes\": \"请将包裹放在门口\"\n" +
            "}\n";

    private final StringRedisTemplate stringRedisTemplate;

    private final RedissonClient redissonClient;

    private final ThreadPoolExecutor threadPoolExecutor;

    private final

    // 定义日期时间格式
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public RedisServiceImpl(StringRedisTemplate stringRedisTemplate, RedissonClient redissonClient, ThreadPoolExecutor threadPoolExecutor) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redissonClient = redissonClient;
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public String getWeatherForecastInformation() throws InterruptedException {
        String cache = stringRedisTemplate.opsForValue().get(MY_HOT_KEY);

        if (StringUtils.isNotBlank(cache)) {
            log.info("缓存命中,直接返回数据");
            return cache;
        }

        RLock lock = redissonClient.getLock(MY_LOCK_KEY);

        boolean isLock = lock.tryLock(1, TimeUnit.SECONDS);
        log.info("获取锁 isLock: {}", isLock);
        if (isLock) {
            try {
                // 模拟耗时操作
                TimeUnit.MILLISECONDS.sleep(500);
                log.info("获取数据完成");

                // 将数据存入缓存
                String data = String.format(msg, LocalDateTime.now().format(formatter));
                stringRedisTemplate.opsForValue().set(MY_HOT_KEY, data, 10, TimeUnit.SECONDS);
                log.info("数据存入缓存完成");

                return data;
            } finally {
                lock.unlock();
            }
        } else {
            log.info("获取锁失败，重试获取数据");
            return getWeatherForecastInformation();
        }
    }

    @Override
    public String getOrderInformation() throws InterruptedException, ExecutionException {
        String cache = stringRedisTemplate.opsForValue().get(MY_EXPIRE_KEY);
        String hotKey = stringRedisTemplate.opsForValue().get(MY_HOT_KEY);

        LocalDateTime now = LocalDateTime.now();
        boolean isBlank = StringUtils.isBlank(cache);
        boolean isExpire = isBlank || now.isAfter(LocalDateTime.from(formatter.parse(cache)));
        log.info("isBlank = {}, isExpire = {}", isBlank, isExpire);
        if (isBlank || isExpire) {
            RLock lock = redissonClient.getLock(MY_LOCK_EXPIRE_KEY);
            long id = Thread.currentThread().getId();
            RFuture<Boolean> booleanRFuture = lock.tryLockAsync(id);
            boolean isLock = booleanRFuture.get();
            log.info("获取锁 isLock: {}", isLock);
            if (isLock) {
                threadPoolExecutor.execute(() -> {
                    // 模拟耗时操作
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                        log.info("获取数据完成");

                        // 将数据存入缓存
                        String data = String.format(msg, LocalDateTime.now().format(formatter));
                        stringRedisTemplate.opsForValue().set(MY_HOT_KEY, data, 100, TimeUnit.SECONDS);
                        stringRedisTemplate.opsForValue().set(MY_EXPIRE_KEY, now.plusSeconds(10).format(formatter), 80, TimeUnit.SECONDS);
                        log.info("数据存入缓存完成");
                    } catch (Exception e) {
                        throw new YuyuException("缓存重构失败", e);
                    } finally {
//                        lock.unlockAsync(id);
                        log.info("释放锁");
                    }
                });
            }
            return stringRedisTemplate.opsForValue().get(MY_HOT_KEY);
        }
        return hotKey;
    }

    @Override
    public String getOrderInformationSpin() throws InterruptedException, ExecutionException {
        String cache = stringRedisTemplate.opsForValue().get(MY_EXPIRE_KEY);
        String hotKey = stringRedisTemplate.opsForValue().get(MY_HOT_KEY);

        boolean isBlank = StringUtils.isBlank(cache);
        boolean isExpire = isBlank || LocalDateTime.now().isAfter(LocalDateTime.from(formatter.parse(cache)));
        log.info("isBlank = {}, isExpire = {}", isBlank, isExpire);
        if (isBlank || isExpire) {
            RLock spinLock = redissonClient.getSpinLock(MY_LOCK_EXPIRE_SPIN_KEY);
            long id = Thread.currentThread().getId();
            RFuture<Boolean> booleanRFuture = spinLock.tryLockAsync(id);
            Boolean isLock = booleanRFuture.get();
            log.info("获取锁 isLock: {}", isLock);
            if (isLock) {
                threadPoolExecutor.execute(() -> {
                    // 模拟耗时操作
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                        log.info("获取数据完成");

                        // 将数据存入缓存
                        String data = String.format(msg, LocalDateTime.now().format(formatter));
                        stringRedisTemplate.opsForValue().set(MY_HOT_KEY, data, 100, TimeUnit.SECONDS);
                        stringRedisTemplate.opsForValue().set(MY_EXPIRE_KEY, LocalDateTime.now().plusSeconds(10).format(formatter), 80, TimeUnit.SECONDS);
                        log.info("数据存入缓存完成");
                    } catch (Exception e) {
                        throw new YuyuException("缓存重构失败", e);
                    } finally {
                        spinLock.unlockAsync(id);
                        log.info("释放锁");
                    }
                });
            }
            return stringRedisTemplate.opsForValue().get(MY_HOT_KEY);
        }
        return hotKey;
    }
}
