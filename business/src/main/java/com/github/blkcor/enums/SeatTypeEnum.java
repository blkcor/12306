package com.github.blkcor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SeatTypeEnum {
    YDZ("1", "一等座", new BigDecimal("0.4")),

    EDZ("2", "二等座", new BigDecimal("0.3")),

    RW("3", "软卧", new BigDecimal("0.6")),

    YW("4", "硬卧", new BigDecimal("0.5"));

    /**
     * 座位类型代码
     */
    private String code;

    /**
     * 座位类型描述
     */
    private String desc;

    /**
     * 每公里单价
     */
    private BigDecimal price;
}
