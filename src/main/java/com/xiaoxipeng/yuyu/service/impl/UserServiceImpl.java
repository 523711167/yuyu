package com.xiaoxipeng.yuyu.service.impl;

import com.xiaoxipeng.yuyu.pojo.User;
import com.xiaoxipeng.yuyu.mapper.UserMapper;
import com.xiaoxipeng.yuyu.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxipeng.yuyu.vo.S;
import org.springframework.stereotype.Service;

import java.util.List;

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
