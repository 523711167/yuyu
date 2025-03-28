package com.xiaoxipeng.auth.authtication;

import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AdminAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private final Set<String> scopes;

    private final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;


    public AdminAuthenticationToken(Authentication clientPrincipal,
                                    @Nullable String username,
                                    @Nullable String password,
                                    @Nullable Set<String> scopes,
                                    @Nullable Map<String, Object> additionalParameters) {
        super(AuthorizationGrantType.CLIENT_CREDENTIALS, clientPrincipal, additionalParameters);
        this.scopes = Collections.unmodifiableSet((scopes != null) ? new HashSet<>(scopes) : Collections.emptySet());
        this.usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
    }

    public Set<String> getScopes() {
        return this.scopes;
    }

    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken() {
        return usernamePasswordAuthenticationToken;
    }
}
