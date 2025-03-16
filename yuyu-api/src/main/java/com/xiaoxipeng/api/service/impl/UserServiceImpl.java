package com.xiaoxipeng.api.service.impl;

import com.xiaoxipeng.api.pojo.User;
import com.xiaoxipeng.api.mapper.UserMapper;
import com.xiaoxipeng.api.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-02-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    public User getUserByUsername(String username) {
        System.out.println(username);
        return lambdaQuery().eq(User::getUsername, username).one();
    }

    @Override
    public void doLogin(String username, String password) {
        System.out.println("Login被执行");
    }

    @Override
    public Integer getNumber() {
        System.out.println("getNumber被执行");
        return 100;
    }


}
