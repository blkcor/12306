package com.github.blkcor.service.impl;

import com.github.blkcor.mapper.MemberMapper;
import com.github.blkcor.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Override
    public int count() {
        return memberMapper.count();
    }
}
