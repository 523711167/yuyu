package com.xiaoxipeng.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        /**
         * 1. corePoolSize 核心线程数
         * 2. maximumPoolSize 最大线程数
         * 3. keepAliveTime 线程空闲时间，控制超出核心线程数的线程的回收时间
         * 4. unit 时间单位
         * 5. workQueue 阻塞队列
         * 6. threadFactory 线程工厂
         * 7. handler 拒绝策略，当线程数达到最大线程数并且阻塞队列已满时，新任务的处理方式
         */
        return new ThreadPoolExecutor(
                8,
                10,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }
}
