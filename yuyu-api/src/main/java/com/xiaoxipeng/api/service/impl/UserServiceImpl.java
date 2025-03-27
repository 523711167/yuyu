package com.xiaoxipeng.api.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxipeng.api.mapper.UserMapper;
import com.xiaoxipeng.api.service.IUserService;
import com.xiaoxipeng.user.dto.UserPageDto;
import com.xiaoxipeng.user.entity.User;
import com.xiaoxipeng.user.vo.UserPageVo;
import com.xiaoxipeng.user.vo.UserVo;
import com.xiaoxipeng.common.vo.PageVo;
import com.xiaoxipeng.common.vo.S;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import static com.xiaoxipeng.auth.constant.Status.USER_ERROR;

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

    @Override
    public S<PageVo<UserPageVo>> listPage(UserPageDto userPageDto) {
        Page<UserPageVo> page = new Page<>();
        page.setCurrent(userPageDto.getCurrent());
        page.setSize(userPageDto.getSize());
        IPage<UserPageVo> pageVo = this.baseMapper.selectPageVo(userPageDto, page);

        PageVo<UserPageVo> data = PageVo.<UserPageVo>builder()
                .pages(pageVo.getPages())
                .current(pageVo.getCurrent())
                .size(pageVo.getSize())
                .total(pageVo.getTotal())
                .data(pageVo.getRecords()).build();

        return S.success(data);
    }
}
