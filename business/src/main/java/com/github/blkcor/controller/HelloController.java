package com.github.blkcor.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.blkcor.exception.BusinessException;
import com.github.blkcor.exception.BusinessExceptionEnum;
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
    @SentinelResource(value= "BLKCOR",blockHandler = "helloFallback")
    public String hello() throws InterruptedException {
        Thread.sleep(500);
        redisTemplate.opsForValue().set("hello","blkcor");
        return "hello blkcor!";
    }

    private String helloFallback(BlockException e){
        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_FLOW_EXCEPTION);
    }

    @GetMapping("/hello/get")
    public String hello1() {
        return redisTemplate.opsForValue().get("hello");
    }
}
