package com.github.blkcor.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainCarriageQueryResp {

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
    private Integer index;

    /**
    * 座位类型|枚举[SeatTypeEnum]
    */
    private String seatType;

    /**
    * 座位数量
    */
    private Integer seatCount;

    /**
    * 行数
    */
    private Integer rowCount;

    /**
    * 列数
    */
    private Integer columnCount;

    /**
    * 新增时间
    */
    private String createTime;

    /**
    * 更新时间
    */
    private String updateTime;
}
