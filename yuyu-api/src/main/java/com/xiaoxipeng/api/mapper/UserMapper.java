package com.xiaoxipeng.api.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoxipeng.dto.UserPageDto;
import com.xiaoxipeng.entity.User;
import com.xiaoxipeng.vo.UserPageVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-03-25
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<UserPageVo> selectPageVo(@Param("userPageDto") UserPageDto userPageDto, Page<UserPageVo> page);

}
