package com.xiaoxipeng.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.xiaoxipeng.authtication.AdminAuthenticationConverter;
import com.xiaoxipeng.authtication.AdminAuthenticationProvider;
import com.xiaoxipeng.authtication.DefaultAuthorizationGrantTypes;
import com.xiaoxipeng.authtication.DefaultOAuth2TokenCustomizer;
import com.xiaoxipeng.util.RsaUtils;
import com.xiaoxipeng.vo.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.DefaultOAuth2AccessTokenResponseMapConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
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
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.util.CollectionUtils;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

import static com.xiaoxipeng.constant.SysClient.ADMIN;

@Configuration
@EnableWebSecurity
@Slf4j
public class DefaultSecurityConfig {

    private final ObjectMapper objectMapper;

    private final JdbcTemplate jdbcTemplate;

    private final DefaultOAuth2TokenCustomizer defaultOAuth2TokenCustomizer;

    @Autowired
    public DefaultSecurityConfig(ObjectMapper objectMapper, JdbcTemplate jdbcTemplate, DefaultOAuth2TokenCustomizer defaultOAuth2TokenCustomizer) {
        this.objectMapper = objectMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.defaultOAuth2TokenCustomizer = defaultOAuth2TokenCustomizer;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        OAuth2AuthorizationServerConfigurer oauth2Server = http.getConfigurer(OAuth2AuthorizationServerConfigurer.class);
        oauth2Server.oidc(Customizer.withDefaults());	// Enable OpenID Connect 1.0
        http.exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                ).requestCache((cache) -> cache
                    .requestCache(new NullRequestCache())
                );
        http.with(oauth2Server, oAuth2AuthorizationServerConfigurer -> {
            oAuth2AuthorizationServerConfigurer.tokenEndpoint(oAuth2TokenEndpointConfigurer -> {
                oAuth2TokenEndpointConfigurer.accessTokenRequestConverter(new AdminAuthenticationConverter());
                oAuth2TokenEndpointConfigurer.authenticationProvider(createAdminProvider());
                oAuth2TokenEndpointConfigurer.accessTokenResponseHandler(accessTokenResponseHandler());
            });
        });
        return http.build();
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
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
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
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("123")
                .password("123")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient yuyuClient = RegisteredClient.withId(UUID.randomUUID().toString())
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

        RegisteredClient otherClient = RegisteredClient.withId(UUID.randomUUID().toString())
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
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

//    @Bean
//    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
//        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
//    }

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
}
