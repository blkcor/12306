package com.github.blkcor.exception;


import lombok.Getter;

@Getter
public enum BusinessExceptionEnum {
    MEMBER_MOBILE_EXIST("会员已存在"),

    MEMBER_MOBILE_NOT_EXIST("请先发送短信验证码"),

    MEMBER_CODE_ERROR("验证码错误"),

    BUSINESS_STATION_NAME_UNIQUE_ERROR("车站已存在"),

    BUSINESS_CARRIAGE_CODE_INDEX_UNIQUE_ERROR("车厢号已存在"),

    BUSINESS_TRAIN_CODE_UNIQUE_ERROR("车次已存在"),

    BUSINESS_TRAIN_STATION_INDEX_UNIQUE_ERROR("同车次站序已存在"),

    BUSINESS_TRAIN_STATION_NAME_UNIQUE_ERROR("同车次站名已存在");

    private String desc;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    BusinessExceptionEnum(String desc) {
        this.desc = desc;
    }


}
