package com.xiaoxipeng.feign;


import com.xiaoxipeng.constant.AppConstant;
import com.xiaoxipeng.vo.R;
import com.xiaoxipeng.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = AppConstant.API,
        fallback = UserClientFallback.class
)
public interface UserClient {

    String USER_API_PREFIX = "/user/";

    @GetMapping(USER_API_PREFIX + "username")
    R<UserVo> getUserByUsername(@RequestParam(required = true) String username);

}
