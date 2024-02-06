package com.github.blkcor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ConfirmOrderStatusEnum {
    INIT("I", "初始化"), PENDING("P", "处理中"), SUCCESS("S", "成功"), FAILURE("F", "失败"), EMPTY("E", "无票"), CANCEL("C", "取消");

    private String code;

    private String desc;
}
