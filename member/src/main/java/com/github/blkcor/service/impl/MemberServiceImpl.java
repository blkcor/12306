package com.github.blkcor.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.blkcor.entity.Member;
import com.github.blkcor.entity.MemberExample;
import com.github.blkcor.exception.BusinessException;
import com.github.blkcor.exception.BusinessExceptionEnum;
import com.github.blkcor.mapper.MemberMapper;
import com.github.blkcor.req.MemberLoginReq;
import com.github.blkcor.req.MemberRegisterReq;
import com.github.blkcor.req.MemberSendCodeReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.MemberLoginResp;
import com.github.blkcor.service.MemberService;
import com.github.blkcor.utils.JwtUtil;
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
        member.setId(IdUtil.getSnowflake(1, 1).nextId());
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
        member.setId(IdUtil.getSnowflake(1, 1).nextId());
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

    @Override
    public CommonResp<MemberLoginResp> login(MemberLoginReq memberLoginReq) {
        //查询手机号是否存在
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(memberLoginReq.getMobile());
        long count = memberMapper.countByExample(memberExample);
        if (count == 0) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }

        //TODO: 验证码校验：从redis中取出验证码，和用户输入的验证码进行比较
        //TODO: 如果验证码校验通过，删除redis中的验证码，将发送记录表中对应的记录标记为已使用

        if (!memberLoginReq.getCode().equals("8888")) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_CODE_ERROR);
        }
        //返回会员的信息
        Member member = memberMapper.selectByExample(memberExample).get(0);
        //生成随机的token
        String token = JwtUtil.createToken(member.getId(), member.getMobile());
        //TODO: 将token存入redis，key为token，value为用户的id
        MemberLoginResp memberLoginResp = new MemberLoginResp();
        memberLoginResp.setToken(token);
        BeanUtil.copyProperties(member, memberLoginResp);
        return CommonResp.success(memberLoginResp);
    }
}
