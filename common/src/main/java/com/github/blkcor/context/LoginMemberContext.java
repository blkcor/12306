package com.github.blkcor.context;

import com.github.blkcor.resp.MemberLoginResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 会员登录上下文
 */
public class LoginMemberContext {
    private static final Logger LOG = LoggerFactory.getLogger(LoginMemberContext.class);
    private static ThreadLocal<MemberLoginResp> member = new ThreadLocal<>();

    public static MemberLoginResp getMember() {
        return member.get();
    }

    public static void setMember(MemberLoginResp memberLoginResp) {
        LOG.info("登录会员信息: {}", memberLoginResp);
        LoginMemberContext.member.set(memberLoginResp);
    }

    public static Long getId() {
        try {
            return getMember().getId();
        } catch (Exception e) {
            LOG.error("获取会员信息失败", e);
            throw e;
        }

    }


}
