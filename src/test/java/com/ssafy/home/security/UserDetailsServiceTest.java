package com.ssafy.home.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.ssafy.home.security.config.CustomSecurityConfig;
import com.ssafy.home.security.controller.SecureController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(value = SecureController.class) // webMvcTest는 지정된 컨트롤러 빈만 로딩. 서비스 이런거 x
@Import(CustomSecurityConfig.class) // 필요한 config만 로딩
public class UserDetailsServiceTest {
	
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passEncoder;

    @Autowired
    MockMvc mockMvc;
    
    @Test
    void passwordEncoderTest() {
        String pass = "1234";
        String e1 = passEncoder.encode(pass);
        log.debug("{},{}", e1);
        Assertions.assertTrue(passEncoder.matches(pass, e1));
    }
}
