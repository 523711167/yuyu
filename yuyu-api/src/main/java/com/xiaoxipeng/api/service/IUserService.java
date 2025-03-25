package com.xiaoxipeng.api.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxipeng.entity.User;
import com.xiaoxipeng.vo.S;
import com.xiaoxipeng.vo.UserVo;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-03-25
 */
public interface IUserService extends IService<User> {

    S<UserVo> getUserByUsername(String username);

}
