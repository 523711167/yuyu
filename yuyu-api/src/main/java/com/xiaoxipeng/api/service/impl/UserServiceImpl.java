package com.xiaoxipeng.api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoxipeng.api.mapper.UserMapper;
import com.xiaoxipeng.api.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxipeng.entity.User;
import com.xiaoxipeng.vo.S;
import com.xiaoxipeng.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import static com.xiaoxipeng.constant.Status.USER_ERROR;

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

    @Override
    public S<UserVo> getUserByUsername(String username) {
        User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        if (user == null) {
            return S.fail(USER_ERROR);
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return S.success(userVo);
    }
}
