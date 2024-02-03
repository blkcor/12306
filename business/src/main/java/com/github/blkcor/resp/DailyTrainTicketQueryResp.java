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
public class DailyTrainTicketQueryResp {

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
    * 始发站
    */
    private String start;

    /**
    * 始发站拼音
    */
    private String startPinyin;

    /**
    * 始发时间
    */
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
    * 出发序号|本次是整个车次的第几站
    */
    private Integer startIndex;

    /**
    * 终点站
    */
    private String end;

    /**
    * 终点站拼音
    */
    private String endPinyin;

    /**
    * 终点时间
    */
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
    * 到达序号|本次是整个车次的第几站
    */
    private Integer endIndex;

    /**
    * 一等座余票
    */
    private Integer ydz;

    /**
    * 一等座票价
    */
    private String ydzPrice;

    /**
    * 二等座余票
    */
    private Integer edz;

    /**
    * 二等座票价
    */
    private String edzPrice;

    /**
    * 软卧余票
    */
    private Integer rw;

    /**
    * 软卧票价
    */
    private String rwPrice;

    /**
    * 硬卧余票
    */
    private Integer yw;

    /**
    * 硬卧票价
    */
    private String ywPrice;

    /**
    * 新增时间
    */
    private String createTime;

    /**
    * 更新时间
    */
    private String updateTime;
}
