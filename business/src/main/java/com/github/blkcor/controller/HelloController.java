package com.github.blkcor.controller;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HelloController {

    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @GetMapping("/hello")
    public String hello() {
        redisTemplate.opsForValue().set("hello","blkcor");
        return "Hello World!";
    }

    @GetMapping("/hello/get")
    public String hello1() {
        return redisTemplate.opsForValue().get("hello");
    }
}
