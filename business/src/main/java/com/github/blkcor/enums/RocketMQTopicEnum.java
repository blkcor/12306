package com.github.blkcor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum RocketMQTopicEnum {

    CONFIRM_ORDER("CONFIRM_ORDER", "确认订单排队");

    private String code;

    private String desc;
}
