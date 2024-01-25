package com.github.blkcor.service;

import com.github.blkcor.req.MemberLoginReq;
import com.github.blkcor.req.MemberRegisterReq;
import com.github.blkcor.req.MemberSendCodeReq;
import com.github.blkcor.req.PassengerSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.MemberLoginResp;

public interface MemberService {
    /**
     * 用户注册
     * @param memberRegisterReq 请求参数
     * @return 返回结果
     */
    CommonResp<Long> register(MemberRegisterReq memberRegisterReq);

    /**
     * 用户发送短信验证码
     * @param memberSendCodeReq 请求参数
     * @return  返回结果
     */
    CommonResp<Integer> sendCode(MemberSendCodeReq memberSendCodeReq);

    /**
     * 用户登录
     * @param memberLoginReq 请求参数
     * @return 返回结果
     */
    CommonResp<MemberLoginResp> login(MemberLoginReq memberLoginReq);

}
