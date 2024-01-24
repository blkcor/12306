package com.github.blkcor.service.impl;


import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.blkcor.entity.Member;
import com.github.blkcor.entity.MemberExample;
import com.github.blkcor.exception.BusinessException;
import com.github.blkcor.exception.BusinessExceptionEnum;
import com.github.blkcor.mapper.MemberMapper;
import com.github.blkcor.req.MemberRegisterReq;
import com.github.blkcor.req.MemberSendCodeReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.service.MemberService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;

    private static final Logger LOG = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Override
    public CommonResp<Long> register(MemberRegisterReq memberRegisterReq) {
        //查询是否有该用户
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(memberRegisterReq.getMobile());
        long count = memberMapper.countByExample(memberExample);
        if (count > 0) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }
        //注册
        Member member = new Member();
        member.setId(IdUtil.createSnowflake(1, 1).nextId());
        member.setMobile(memberRegisterReq.getMobile());
        memberMapper.insertSelective(member);
        return CommonResp.success(member.getId());
    }

    @Override
    public CommonResp<Integer> sendCode(MemberSendCodeReq memberSendCodeReq) {
        //查询手机号是否被注册过
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(memberSendCodeReq.getMobile());
        long count = memberMapper.countByExample(memberExample);
        if (count > 0) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }
        //如果没有被注册，需要插入一条记录
        Member member = new Member();
        member.setId(IdUtil.createSnowflake(1, 1).nextId());
        member.setMobile(memberSendCodeReq.getMobile());
        memberMapper.insertSelective(member);

        /*
         * 发送验证码
         * 1、随机生成验证码
         * 2、存入redis：手机号、短信验证码、有效期、是否被使用、业务类型
         * 3、存入短信发送记录表：手机号、短信验证码、有效期、是否被使用、业务类型
         */
        int code = RandomUtil.randomInt(100000, 999999);
        LOG.info("生成验证码：{}", code);
        return CommonResp.success(code);
    }
}
