package com.xiaoxipeng.api.pojo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends Base{

    private String username;

    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
