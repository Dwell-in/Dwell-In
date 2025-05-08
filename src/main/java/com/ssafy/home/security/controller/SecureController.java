package com.ssafy.home.security.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/secured")
@Slf4j
public class SecureController {
	
    @GetMapping("/admin")
    // @AuthenticationPrincipal로 UserDetails 획득 후 사용
    public String admin(@AuthenticationPrincipal UserDetails details) {
        if (details != null) {
            log.debug("로그인 정보: {}", details);
        }
        return "secured/admin/admin";
    }
    
    @GetMapping("/user")
    public String user() {
        return "secured/user/user";
    }
}

