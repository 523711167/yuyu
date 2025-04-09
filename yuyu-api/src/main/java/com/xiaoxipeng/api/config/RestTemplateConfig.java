package com.xiaoxipeng.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

//        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
//        fixedBackOffPolicy.setBackOffPeriod(5000l);
//        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        // 设置指数回退策略
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        // 初始间隔时间（毫秒）
        backOffPolicy.setInitialInterval(1000);
        // 每次重试后间隔时间的增长倍数
        backOffPolicy.setMultiplier(1.3);
        // 最大间隔时间（毫秒）
        backOffPolicy.setMaxInterval(120000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(200);
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }

}
