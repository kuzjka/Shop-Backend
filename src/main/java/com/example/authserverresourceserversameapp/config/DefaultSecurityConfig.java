package com.example.authserverresourceserversameapp.config;


import com.example.authserverresourceserversameapp.service.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity

public class DefaultSecurityConfig {
    private final AppUserDetailsService userDetailsService;
    private final Converter converter;

    public DefaultSecurityConfig(AppUserDetailsService userDetailsService, Converter converter) {
        this.userDetailsService = userDetailsService;
        this.converter = converter;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"),
                                        AntPathRequestMatcher.antMatcher("/user/**"),
                                        AntPathRequestMatcher.antMatcher("/images/**"))
                                .permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/order/**"))
                                .hasRole("user")
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/**"))
                                .hasAnyRole("user", "manager", "admin")
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/**"))
                                .hasAnyRole("manager", "admin")
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/**"))
                                .hasRole("admin")
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/**"))
                                .hasRole("admin"))
                .formLogin(withDefaults());
        http.cors(withDefaults());
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        http.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(jwtConfigurer -> jwtConfigurer
                        .jwtAuthenticationConverter(converter))).apply(authorizationServerConfigurer);
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}