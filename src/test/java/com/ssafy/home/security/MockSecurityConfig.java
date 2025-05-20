package com.ssafy.home.security;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ssafy.home.security.controller.RestAccessDeniedHandler;
import com.ssafy.home.security.controller.RestAuthenticationEntryPoint;
import com.ssafy.home.security.jwt.JwtTokenProvider;

@TestConfiguration
public class MockSecurityConfig {

    @Bean
    JwtTokenProvider jwtTokenProvider() {
        return Mockito.mock(JwtTokenProvider.class);
    }

    @Bean
    RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return Mockito.mock(RestAuthenticationEntryPoint.class);
    }

    @Bean
    RestAccessDeniedHandler restAccessDeniedHandler() {
        return Mockito.mock(RestAccessDeniedHandler.class);
    }
    
    @Bean
    UserDetailsService userDetailsService() {
        return Mockito.mock(UserDetailsService.class);
    }
}
