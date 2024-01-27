package com.github.blkcor.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainQueryResp {

    /**
    * id
    */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
    * 车次编号
    */
    private String code;

    /**
    * 车次类型|枚举[TrainTypeEnum]
    */
    private String type;

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
    private String startTime;

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
    private String endTime;

    /**
    * 新增时间
    */
    private String createTime;

    /**
    * 更新时间
    */
    private String updateTime;
}
