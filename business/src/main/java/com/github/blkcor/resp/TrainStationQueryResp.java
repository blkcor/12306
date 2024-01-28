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
public class TrainStationQueryResp {

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
    * 站序号
    */
    private Integer index;

    /**
    * 站名
    */
    private String name;

    /**
    * 站名拼音
    */
    private String namePinyin;

    /**
    * 进站时间
    */
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date inTime;

    /**
    * 出站时间
    */
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date outTime;

    /**
    * 停留时间(前端自动计算填充)
    */
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date stopTime;

    /**
    * 公里数|从上一站到本站的距离
    */
    private String km;

    /**
    * 新增时间
    */
    private String createTime;

    /**
    * 更新时间
    */
    private String updateTime;
}
