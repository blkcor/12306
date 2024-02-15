package com.github.blkcor.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import jakarta.annotation.Resource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HelloController {

    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private Environment environment;
    @GetMapping("/hello")
    @SentinelResource(value= "hello",fallback = "helloFallback")
    public String hello() {
        String transport = environment.getProperty("spring.cloud.sentinel.transport.dashboard");
        String port = environment.getProperty("spring.cloud.sentinel.transport.port");
        redisTemplate.opsForValue().set("hello","blkcor");
        return port + " " + transport;
    }

    private String helloFallback(){
        return "请求被限流";
    }

    @GetMapping("/hello/get")
    public String hello1() {
        return redisTemplate.opsForValue().get("hello");
    }
}
