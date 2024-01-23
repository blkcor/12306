package com.github.blkcor.service.impl;


import com.github.blkcor.entity.Member;
import com.github.blkcor.entity.MemberExample;
import com.github.blkcor.mapper.MemberMapper;
import com.github.blkcor.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;

    @Override
    public Long register(String mobile) {
        //查询是否有该用户
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        long count = memberMapper.countByExample(memberExample);
        if (count > 0) {
            throw new RuntimeException("该用户已经注册");
        }
        //注册
        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);
        memberMapper.insertSelective(member);
        return member.getId();
    }
}
