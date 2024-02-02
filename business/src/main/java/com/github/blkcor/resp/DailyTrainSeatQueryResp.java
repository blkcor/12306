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
public class DailyTrainSeatQueryResp {

    /**
    * id
    */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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
    * 售票状态|将经过的车站使用01来拼接，0代表未售，1代表已售
    */
    private String sell;

    /**
    * 新增时间
    */
    private String createTime;

    /**
    * 更新时间
    */
    private String updateTime;
}
