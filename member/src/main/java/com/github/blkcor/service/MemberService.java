package com.github.blkcor.service;

import com.github.blkcor.req.MemberRegisterReq;
import com.github.blkcor.resp.CommonResp;

public interface MemberService {
    CommonResp<Long> register(MemberRegisterReq memberRegisterReq);

}
