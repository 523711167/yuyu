package com.xiaoxipeng.dto;


import lombok.Getter;
import lombok.Setter;

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

}
