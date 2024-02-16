package com.github.blkcor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum RedisKeyPrefixEnum {
    CONFIRM_ORDER("LOCK_CONFIRM_ORDER", "购票锁"), SK_TOKEN("LOCK_SK_TOKEN", "令牌锁"), SK_TOKEN_COUNT("LOCK_SK_TOKEN_COUNT", "令牌数");

    private String code;

    private String desc;
}
