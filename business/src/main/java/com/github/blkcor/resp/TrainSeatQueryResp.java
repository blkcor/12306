package com.github.blkcor.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainSeatQueryResp {

    /**
    * id
    */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
    * 车次编号
    */
    private String trainCode;

    /**
    * 车厢序号
    */
    private Integer carriageIndex;

    /**
    * 排号|01,02
    */
    private String row;

    /**
    * 列号|枚举[SeatColEnum]
    */
    private String col;

    /**
    * 座位类型|枚举[SeatTypeEnum]
    */
    private String seatType;

    /**
    * 车厢座位序号
    */
    private Integer carriageSeatIndex;

    /**
    * 新增时间
    */
    private String createTime;

    /**
    * 更新时间
    */
    private String updateTime;
}
