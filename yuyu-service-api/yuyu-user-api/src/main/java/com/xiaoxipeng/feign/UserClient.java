package com.xiaoxipeng.feign;


import com.xiaoxipeng.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = AppConstant.API,
        fallback = UserClientFallback.class
)
public interface UserClient {
}
