package com.xiaoxipeng.feign;


import com.xiaoxipeng.vo.R;
import com.xiaoxipeng.vo.UserVo;
import org.springframework.stereotype.Component;

/**
 * fallback只会发生在服务降级的情况下
 */
@Component
public class UserClientFallback implements UserClient{

    @Override
    public R<UserVo> getUserByUsername(String username) {
        return R.fail();
    }
}
