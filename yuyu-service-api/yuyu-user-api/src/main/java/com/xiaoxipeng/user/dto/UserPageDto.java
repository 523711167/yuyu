package com.xiaoxipeng.user.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserPageDto {

    /**
     * 当前页
     */
    private Long current;

    /**
     * 每页显示条数
     */
    private Long size;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 状态
     */
    private Integer status;

    private LocalDateTime fromLastLoginTime;

    private LocalDateTime toLastLoginTime;

}
