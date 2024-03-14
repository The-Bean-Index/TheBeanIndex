package com.bbdgrad.thebeanindex.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Order(1)
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${authentication.enabled:true}")
    private boolean authenticationEnabled;

    @Value("${google.client.id}")
    private String googleClientId;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll())
            .addFilterBefore(new AccessTokenFilter(authenticationEnabled, googleClientId), BasicAuthenticationFilter.class)
            .build();
    }
}
