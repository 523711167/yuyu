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

}
