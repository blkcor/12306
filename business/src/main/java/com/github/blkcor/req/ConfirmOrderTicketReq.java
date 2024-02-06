package com.github.blkcor.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmOrderTicketReq {
    @NotNull(message = "乘客id不能为空")
    private Long passengerId;

    @NotNull(message = "乘客票种类型不能为空")
    private String passengerType;

    @NotNull(message = "乘客姓名不能为空")
    private String passengerName;

    @NotNull(message = "乘客身份证不能为空")
    private String passengerIdCard;

    @NotNull(message = "座位类型code不能为空")
    private String seatTypeCode;

    /**
     * 可空，表示随机座位
     */
    private String seat;
}
