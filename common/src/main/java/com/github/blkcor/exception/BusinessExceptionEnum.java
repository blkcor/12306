package com.github.blkcor.exception;


import lombok.Getter;

@Getter
public enum BusinessExceptionEnum {
    MEMBER_MOBILE_EXIST("会员已存在");

    private String desc;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    BusinessExceptionEnum(String desc) {
        this.desc = desc;
    }


}
