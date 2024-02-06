package com.github.blkcor.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmOrderQueryResp {

    /**
    * id
    */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
    * 会员id
    */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long memberId;

    /**
    * 日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    /**
    * 车次编号
    */
    private String trainCode;

    /**
    * 始发站
    */
    private String start;

    /**
    * 终点站
    */
    private String end;

    /**
    * 每日车次余票表id
    */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dailyTrainTicketId;

    /**
    * 购票信息
    */
    private String tickets;

    /**
    * 订单状态|枚举[ConfirmOrderStatusEnum]
    */
    private String status;

    /**
    * 新增时间
    */
    private String createTime;

    /**
    * 更新时间
    */
    private String updateTime;
}
