package com.github.blkcor.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.blkcor.context.LoginMemberContext;
import com.github.blkcor.resp.MemberLoginResp;
import com.github.blkcor.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MemberInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(MemberInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.info("进入memberInterceptor拦截器");
        /*
         * 1、从header中获取token
         * 2、从token中获取参数并且设置到线程本地变量中
         */
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (ObjectUtil.isNotEmpty(token)) {
            LOG.info("获取到会员登录token: {}", token);
            JSONObject jsonObject = JwtUtil.getJSONObject(token);
            //将jsonObject转换为MemberLoginResp对象
            MemberLoginResp memberLoginResp = JSONUtil.toBean(jsonObject, MemberLoginResp.class);
            assert memberLoginResp != null;
            memberLoginResp.setToken(token);
            LoginMemberContext.setMember(memberLoginResp);
        }
        LOG.info("退出memberInterceptor拦截器");
        return true;
    }
}
