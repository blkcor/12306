package com.github.blkcor.controller;

import com.github.blkcor.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Resource
    private MemberService memberService;

    @GetMapping("/hello")
    public String hello() {
            return "Hello World";
    }

    @GetMapping("/count")
    public String count() {
            return "Count: " + memberService.count();
    }
}
