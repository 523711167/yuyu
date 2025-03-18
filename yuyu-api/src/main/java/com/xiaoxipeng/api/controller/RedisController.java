package com.xiaoxipeng.api.controller;

import com.xiaoxipeng.api.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/redis/")
public class RedisController {

    private final IRedisService redisService;

    @Autowired
    public RedisController(IRedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping("testHotKeyMutex")
    public String testHotKeyMutex() throws InterruptedException {
        return redisService.getWeatherForecastInformation();
    }

    @PostMapping("testHotKeyExpire")
    public String testHotKeyExpire() throws InterruptedException, ExecutionException {
        return redisService.getOrderInformation();
    }

    @PostMapping("testHotKeyExpireSpin")
    public String getOrderInformationSpin() throws InterruptedException, ExecutionException {
        return redisService.getOrderInformationSpin();
    }
}
