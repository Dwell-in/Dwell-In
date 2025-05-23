package com.ssafy.home.common.contoller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

    private final StringRedisTemplate redisTemplate;

    @GetMapping("/set")
    public String setValue() {
        redisTemplate.opsForValue().set("testkey", "hello");
        return "OK";
    }

    @GetMapping("/get")
    public String getValue() {
        return redisTemplate.opsForValue().get("testkey");
    }
}

