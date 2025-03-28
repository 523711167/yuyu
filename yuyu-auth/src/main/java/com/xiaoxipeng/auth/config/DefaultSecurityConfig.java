package com.xiaoxipeng.auth.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.xiaoxipeng.auth.authtication.AdminAuthenticationConverter;
import com.xiaoxipeng.auth.authtication.AdminAuthenticationProvider;
import com.xiaoxipeng.auth.authtication.DefaultAuthorizationGrantTypes;
import com.xiaoxipeng.auth.authtication.DefaultOAuth2TokenCustomizer;
import com.xiaoxipeng.auth.util.RsaUtils;
import com.xiaoxipeng.common.vo.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.DefaultOAuth2AccessTokenResponseMapConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static com.xiaoxipeng.auth.constant.SysClient.ADMIN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
@EnableWebSecurity
@Slf4j
public class DefaultSecurityConfig {

    private final ObjectMapper objectMapper;

    private final JdbcTemplate jdbcTemplate;

    private final DefaultOAuth2TokenCustomizer defaultOAuth2TokenCustomizer;

    private final UserDetailsService defaultUserDetailsService;

    @Autowired
    public DefaultSecurityConfig(ObjectMapper objectMapper,
                                 JdbcTemplate jdbcTemplate,
                                 DefaultOAuth2TokenCustomizer defaultOAuth2TokenCustomizer,
                                 UserDetailsService defaultUserDetailsService) {
        this.objectMapper = objectMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.defaultOAuth2TokenCustomizer = defaultOAuth2TokenCustomizer;
        this.defaultUserDetailsService = defaultUserDetailsService;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        OAuth2AuthorizationServerConfigurer oauth2Server =
                http.getConfigurer(OAuth2AuthorizationServerConfigurer.class);

        http.exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login/oauth2/code/**"),
                                new AntPathRequestMatcher("/user/")
                        )
                        .defaultAuthenticationEntryPointFor(
                                this::jsonAuthenticationEntryPoint,
                                new AntPathRequestMatcher("/**")
                        )
                        .accessDeniedHandler(this::jsonAccessDeniedHandler)
                ).requestCache((cache) -> cache
                    .requestCache(new NullRequestCache())
                );

        http.with(oauth2Server, serverConfigurer -> {
            serverConfigurer.tokenEndpoint(tokenEndpointConfigurer -> {
                tokenEndpointConfigurer.accessTokenRequestConverter(new AdminAuthenticationConverter());
                tokenEndpointConfigurer.authenticationProvider(createAdminProvider());
                tokenEndpointConfigurer.accessTokenResponseHandler(accessTokenResponseHandler());
            });
            // Enable OpenID Connect 1.0
            serverConfigurer.oidc(oidcConfigurer -> {
                oidcConfigurer.userInfoEndpoint(userInfoEndpointConfigurer -> {
                    userInfoEndpointConfigurer.errorResponseHandler(this::jsonAuthenticationEntryPoint);
                    userInfoEndpointConfigurer.userInfoResponseHandler(this::userInfoResponseHandler);
                });
            });
        });
        http.oauth2ResourceServer(resourceServer ->{
            resourceServer.jwt(jwtConfigurer -> {
                jwtConfigurer.decoder(jwtDecoder());
            });
        });

        http.csrf((csrf) -> csrf.disable());
        return http.build();
    }



    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                .formLogin(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient yuyuClient = RegisteredClient.withId("2e2697b5-64de-490d-943c-0c944ee95022")
                .clientId("yuyu")
                .clientSecret("{noop}123456")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/my-oidc-client")
                .postLogoutRedirectUri("http://127.0.0.1:8080/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        RegisteredClient otherClient = RegisteredClient.withId("c6b79c18-b7ef-44b8-b1b2-59152f05f133")
                .clientId(ADMIN)
                .clientSecret("{noop}123123")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(DefaultAuthorizationGrantTypes.ADMIN_PASSWORD)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/my-oidc-client")
                .postLogoutRedirectUri("http://127.0.0.1:8080/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        return new InMemoryRegisteredClientRepository(yuyuClient, otherClient);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource()  {
        KeyPair keyPair = RsaUtils.loadKeyPair("rsa/private.pem", "rsa/public.pem", "RSA");
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID("e1840df9-7fcd-4ca1-aa99-7ecd0f9540f0")
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource());
    }

    @Bean
    public JwtEncoder jwtEncoder()  {
        return new NimbusJwtEncoder(jwkSource());
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }


    @Bean
    public OAuth2AuthorizationService authorizationService() {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate ,registeredClientRepository());
    }

    @Bean
    public OAuth2TokenGenerator<? extends OAuth2Token> oAuth2TokenGenerator() {
        JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder());
        // ID_Token 自定义claims
        jwtGenerator.setJwtCustomizer(defaultOAuth2TokenCustomizer);
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }

    private  AuthenticationSuccessHandler accessTokenResponseHandler() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            if (!(authentication instanceof OAuth2AccessTokenAuthenticationToken accessTokenAuthentication)) {
                if (log.isErrorEnabled()) {
                    log.error(Authentication.class.getSimpleName() + " must be of type "
                            + OAuth2AccessTokenAuthenticationToken.class.getName() + " but was "
                            + authentication.getClass().getName());
                }
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "Unable to process the access token response.", null);
                throw new OAuth2AuthenticationException(error);
            }

            OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
            OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
            Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

            OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                    .tokenType(accessToken.getTokenType())
                    .scopes(accessToken.getScopes());
            if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
                builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
            }
            if (refreshToken != null) {
                builder.refreshToken(refreshToken.getTokenValue());
            }
            if (!CollectionUtils.isEmpty(additionalParameters)) {
                builder.additionalParameters(additionalParameters);
            }

            OAuth2AccessTokenResponse accessTokenResponse = builder.build();

            try(ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);) {
                Converter<OAuth2AccessTokenResponse, Map<String, Object>> accessTokenResponseParametersConverter = new DefaultOAuth2AccessTokenResponseMapConverter();
                Map<String, Object> tokenResponseParameters = accessTokenResponseParametersConverter.convert(accessTokenResponse);
                HttpServletResponse servletResponse = httpResponse.getServletResponse();
                servletResponse.addHeader("content-type", "application/json");
                servletResponse.getWriter().print(objectMapper.writeValueAsString(R.success(tokenResponseParameters)));
            }

        };
    }

    private void jsonAccessDeniedHandler(HttpServletRequest request,
                                         HttpServletResponse response,
                                         AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE); // 设置内容类型和字符编码
        response.setCharacterEncoding("UTF-8");
        try {
            if (accessDeniedException instanceof AuthorizationDeniedException) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(objectMapper.writeValueAsString(R.accessDenied()));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(objectMapper.writeValueAsString(R.accessDenied()));
            }
        } catch (JsonProcessingException e) {
            log.error("Json序列化异常", e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{}");
        }
    }


    /**
     *  认证异常返回处理
     *      用户名密码端点，认证失败
     *      userinfo端点，认证失败
     */
    private void jsonAuthenticationEntryPoint(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException authException) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE); // 设置内容类型和字符编码
        response.setCharacterEncoding("UTF-8");
        try {
            // 用户名密码不正确
            if (authException instanceof BadCredentialsException) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(objectMapper.writeValueAsString(R.badCredentials()));
            //匿名登录权限不满足
            } else if (authException instanceof InsufficientAuthenticationException) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(objectMapper.writeValueAsString(R.accessDenied()));
            //访问userinfo端口权限不满足
            } else if (authException instanceof OAuth2AuthenticationException) {
                OAuth2Error error = ((OAuth2AuthenticationException) authException).getError();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(objectMapper.writeValueAsString(R.accessDenied()));
            } else {
                log.error("认证发生异常 ===> ", authException);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(objectMapper.writeValueAsString(R.fail()));
            }
        } catch (JsonProcessingException e) {
            log.error("Json序列化异常", e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{}");
        }
    }

    private void userInfoResponseHandler(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Authentication authentication) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        try {
            if (authentication instanceof OidcUserInfoAuthenticationToken userInfoAuthentication) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(objectMapper.writeValueAsString(R.success(userInfoAuthentication.getUserInfo().getClaims())));
            }
        } catch (JsonProcessingException e) {
            log.error("Json序列化异常", e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{}");
        }
    }


    private AdminAuthenticationProvider createAdminProvider()  {
        AdminAuthenticationProvider authenticationProvider = new AdminAuthenticationProvider();
        authenticationProvider.setDaoAuthenticationProvider(createAdminDaoProvider());
        authenticationProvider.setTokenGenerator(oAuth2TokenGenerator());
        authenticationProvider.setSessionRegistry(new SessionRegistryImpl());
        authenticationProvider.setAuthorizationService(authorizationService());
        return authenticationProvider;
    }

    private DaoAuthenticationProvider createAdminDaoProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(defaultUserDetailsService);
        return daoAuthenticationProvider;
    }
}
