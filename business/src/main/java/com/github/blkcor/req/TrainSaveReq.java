package com.github.blkcor.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainSaveReq {
    /**
     * id
     */
    private Long id;
    /**
     * 车次编号
     */
    @NotBlank(message = "车次编号不能为空")
    private String code;
    /**
     * 车次类型|枚举[TrainTypeEnum]
     */
    @NotBlank(message = "车次类型不能为空")
    private String type;
    /**
     * 始发站
     */
    @NotBlank(message = "始发站不能为空")
    private String start;
    /**
     * 始发站拼音
     */
    @NotBlank(message = "始发站拼音不能为空")
    private String startPinyin;
    /**
     * 始发时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "始发时间不能为空")
    private Date startTime;
    /**
     * 终点站
     */
    @NotBlank(message = "终点站不能为空")
    private String end;
    /**
     * 终点站拼音
     */
    @NotBlank(message = "终点站拼音不能为空")
    private String endPinyin;
    /**
     * 终点时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "终点时间不能为空")
    private Date endTime;
    /**
     * 新增时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
}
