package com.xiaoxipeng.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-03-25
 */
@Getter
@Setter
@ToString
public class User extends Base {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码(加密存储)
     */
    private String password;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别: 0-女, 1-男, 2-其他
     */
    private Boolean gender;

    /**
     * 出生日期
     */
    private LocalDate birthDate;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 地址
     */
    private String address;

    /**
     * 状态: 0-禁用, 1-启用
     */
    private Boolean status;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 用户类型: 0-普通用户, 1-管理员, 2-其他
     */
    private Boolean userType;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
}
