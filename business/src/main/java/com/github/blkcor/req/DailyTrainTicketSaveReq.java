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
public class DailyTrainTicketSaveReq {
    /**
     * id
     */
    private Long id;
    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull(message = "日期不能为空")
    private Date date;
    /**
     * 车次编号
     */
    @NotBlank(message = "车次编号不能为空")
    private String trainCode;
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
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "始发时间不能为空")
    private Date startTime;
    /**
     * 出发序号|本次是整个车次的第几站
     */
    @NotNull(message = "出发序号不能为空")
    private Integer startIndex;
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
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "终点时间不能为空")
    private Date endTime;
    /**
     * 到达序号|本次是整个车次的第几站
     */
    @NotNull(message = "到达序号不能为空")
    private Integer endIndex;
    /**
     * 一等座余票
     */
    @NotNull(message = "一等座余票不能为空")
    private Integer ydz;
    /**
     * 一等座票价
     */
    @NotBlank(message = "一等座票价不能为空")
    private String ydzPrice;
    /**
     * 二等座余票
     */
    @NotNull(message = "二等座余票不能为空")
    private Integer edz;
    /**
     * 二等座票价
     */
    @NotBlank(message = "二等座票价不能为空")
    private String edzPrice;
    /**
     * 软卧余票
     */
    @NotNull(message = "软卧余票不能为空")
    private Integer rw;
    /**
     * 软卧票价
     */
    @NotBlank(message = "软卧票价不能为空")
    private String rwPrice;
    /**
     * 硬卧余票
     */
    @NotNull(message = "硬卧余票不能为空")
    private Integer yw;
    /**
     * 硬卧票价
     */
    @NotBlank(message = "硬卧票价不能为空")
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
