package com.xiaoxipeng.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 开启feign客户端调用日志,用于开发测试环境
 */
@Configuration
public class FooConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}

