package com.github.blkcor.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationQueryResp {

    /**
    * id
    */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
    * 站名
    */
    private String name;

    /**
    * 站名拼音
    */
    private String namePinyin;

    /**
    * 站名拼音首字母
    */
    private String namePy;

    /**
    * 新增时间
    */
    private String createTime;

    /**
    * 更新时间
    */
    private String updateTime;
}
