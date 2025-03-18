package com.xiaoxipeng.api.service;

import java.util.concurrent.ExecutionException;

public interface IRedisService {

    /**
     * 模拟解决热点Key问题
     * 分布式锁解决
     * @return
     */
    String getWeatherForecastInformation() throws InterruptedException;

    /**
     * 模拟解决热点Key问题
     * 分布式锁解决
     * @return
     */
    String getOrderInformation() throws InterruptedException, ExecutionException;

    /**
     * 模拟解决热点Key问题
     * 分布式锁解决
     * @return
     */
    String getOrderInformationSpin() throws InterruptedException, ExecutionException;
}
