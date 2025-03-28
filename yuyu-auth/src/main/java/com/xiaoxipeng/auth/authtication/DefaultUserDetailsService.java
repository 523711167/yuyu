package com.xiaoxipeng.auth.authtication;

import com.xiaoxipeng.user.feign.UserClient;
import com.xiaoxipeng.user.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserClient userClient;

    @Autowired
    public DefaultUserDetailsService(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo user = userClient.getUserByUsername(username).getData();
        if (user == null) {
            throw new UsernameNotFoundException("用户名密码不存在");
        }

        UserDetails detail = User.withUsername(user.getUsername())
                .password(user.getPassword())
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(false)
                .authorities(new ArrayList<>())
                .build();
        return detail;
    }
}
