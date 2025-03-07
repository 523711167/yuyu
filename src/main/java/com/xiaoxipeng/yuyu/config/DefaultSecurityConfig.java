package com.xiaoxipeng.yuyu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class DefaultSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requestMatcher -> {
                    requestMatcher.requestMatchers("/favicon.ico").permitAll()
                            .anyRequest().authenticated();
                })
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }

}
