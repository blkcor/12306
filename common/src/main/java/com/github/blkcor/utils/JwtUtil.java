package com.github.blkcor.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.blkcor.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 生成token
     *
     * @param id     用户id
     * @param mobile 手机号
     * @return token
     */
    public static String createToken(Long id, String mobile) {
        DateTime now = DateTime.now();
        DateTime expireTime = now.offsetNew(DateField.HOUR, 24);
        //payload map
        Map<String, Object> map = new HashMap<>();
        //签发时间
        map.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        map.put(JWTPayload.EXPIRES_AT, expireTime);
        //生效时间
        map.put(JWTPayload.NOT_BEFORE, now);
        //内容
        map.put("id", id);
        map.put("mobile", mobile);
        //生成token
        String token = JWTUtil.createToken(map, Constants.KEY.getBytes());
        LOG.info("生成token: {}", token);
        return token;
    }

    public static boolean validToken(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(Constants.KEY.getBytes());
        boolean verify = jwt.verify();
        LOG.info("校验token，结果: {}", verify ? "成功" : "失败");
        return verify;
    }

    /**
     * 根据token获取原始内容
     *
     * @param token token
     * @return 原始内容
     */
    public static JSONObject getJSONObject(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(Constants.KEY.getBytes());
        JSONObject payloads = jwt.getPayloads();
        payloads.remove(JWTPayload.ISSUED_AT);
        payloads.remove(JWTPayload.EXPIRES_AT);
        payloads.remove(JWTPayload.NOT_BEFORE);
        //获取原始内容
        LOG.info("根据token获取原始内容: {}", payloads);
        return payloads;
    }

    public static void main(String[] args) {
        String token = createToken(1L, "18888888888");
        validToken(token);
        getJSONObject(token);
    }

}
