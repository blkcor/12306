package com.github.blkcor.controller;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class TestController {
    @Value("${test.name}")
    private String name;

    @Resource
    private Environment environment;
    @GetMapping("/hello")
    public String hello(){
        String port = environment.getProperty("local.server.port");
        return name + "端口:" + port;
    }
}
