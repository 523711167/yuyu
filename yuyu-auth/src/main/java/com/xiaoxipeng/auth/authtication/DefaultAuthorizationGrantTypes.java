package com.xiaoxipeng.auth.authtication;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

public class DefaultAuthorizationGrantTypes {

    public static final AuthorizationGrantType ADMIN_PASSWORD = new AuthorizationGrantType("password_admin");

}
