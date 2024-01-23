package com.github.blkcor.controller;

import com.github.blkcor.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Resource
    private MemberService memberService;

    @PostMapping("/register")
    public Long register(String mobile) {
        return memberService.register(mobile);
    }

}
