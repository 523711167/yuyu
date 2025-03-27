package com.xiaoxipeng.api.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxipeng.user.dto.UserPageDto;
import com.xiaoxipeng.user.entity.User;
import com.xiaoxipeng.user.vo.UserPageVo;
import com.xiaoxipeng.user.vo.UserVo;
import com.xiaoxipeng.vo.PageVo;
import com.xiaoxipeng.vo.S;

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

    S<PageVo<UserPageVo>> listPage(UserPageDto userPageDto);

}
