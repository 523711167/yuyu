package com.xiaoxipeng.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import static com.xiaoxipeng.common.constant.AppConstant.SCAN_PACKAGE_API;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {SCAN_PACKAGE_API})
public class AuthApplication
{
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
