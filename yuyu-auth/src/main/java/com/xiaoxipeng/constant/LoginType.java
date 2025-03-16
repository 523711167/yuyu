package com.xiaoxipeng.constant;


import lombok.Getter;

@Getter
public enum LoginType {

    ADMIN("admin", "系统使用密码登录");


    private String loginType;

    private String desc;

    LoginType(String loginType, String desc) {
        this.loginType = loginType;
        this.desc = desc;
    }


}
