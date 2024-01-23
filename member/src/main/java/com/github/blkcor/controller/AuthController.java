package com.github.blkcor.controller;

import com.github.blkcor.req.MemberRegisterReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Resource
    private MemberService memberService;

    @PostMapping("/register")
    public CommonResp<Long> register(@RequestBody @Valid MemberRegisterReq memberRegisterReq) {
        return memberService.register(memberRegisterReq);
    }

}
