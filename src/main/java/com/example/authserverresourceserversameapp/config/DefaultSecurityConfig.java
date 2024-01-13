package com.example.authserverresourceserversameapp.config;


import com.example.authserverresourceserversameapp.service.AppUserDetailsService;
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
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers("/user/**", "/images/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/**", "/order/**")
                                .hasAnyAuthority("ROLE_user", "ROLE_admin")
                                .requestMatchers(HttpMethod.POST, "/api/**", "/order/**")
                                .hasAuthority("ROLE_admin")
                                .requestMatchers(HttpMethod.PUT, "/api/**", "/order/**")
                                .hasAuthority("ROLE_admin")
                                .requestMatchers(HttpMethod.DELETE, "/api/**", "/order/**")
                                .hasAuthority("ROLE_admin")
                )
                .formLogin(withDefaults());
        http.cors(withDefaults());
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        http.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(jwtConfigurer -> jwtConfigurer
                        .jwtAuthenticationConverter(converter)))
                .apply(authorizationServerConfigurer);
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