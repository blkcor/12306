package com.github.blkcor.exception;


import lombok.Getter;

@Getter
public enum BusinessExceptionEnum {
    MEMBER_MOBILE_EXIST("会员已存在"),

    MEMBER_MOBILE_NOT_EXIST("请先发送短信验证码"),

    MEMBER_CODE_ERROR("验证码错误");

    private String desc;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    BusinessExceptionEnum(String desc) {
        this.desc = desc;
    }


}
