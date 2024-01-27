package com.github.blkcor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TrainTypeEnum {
    G("G", "高铁", new BigDecimal("1.2")),

    D("D", "动车", new BigDecimal("1.0")),

    Z("Z", "直达", new BigDecimal("0.8"));

    /**
     * 火车类型代码
     */
    private String code;

    /**
     * 火车类型描述
     */
    private String desc;
    /**
     * 票价比例：例如1.1，则票价= 1.1* 每公里单价(SeatTypeEnum.price) * 公里(station.distance)
     */
    private BigDecimal priceRate;
}
