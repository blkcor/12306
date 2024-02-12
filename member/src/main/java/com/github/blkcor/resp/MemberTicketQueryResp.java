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
public class MemberTicketQueryResp {

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
    * 乘客id
    */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long passengerId;

    /**
    * 乘客姓名
    */
    private String passengerName;

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
    * 车厢索引
    */
    private Integer carriageIndex;

    /**
    * 行号|01、02
    */
    private String row;

    /**
    * 列号|枚举[SeatColEnum]
    */
    private String col;

    /**
    * 出发站
    */
    private String start;

    /**
    * 出发时间
    */
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
    * 终点站
    */
    private String end;

    /**
    * 到站时间
    */
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
    * 座位类型|枚举[SeatTypeEnum]
    */
    private String seatType;

    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
    * 更新时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
