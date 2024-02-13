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
public class MemberTicketSaveReq {
    /**
     * id
     */
    private Long id;
    /**
     * 会员id
     */
    @NotNull(message = "会员id不能为空")
    private Long memberId;
    /**
     * 乘客id
     */
    @NotNull(message = "乘客id不能为空")
    private Long passengerId;
    /**
     * 乘客姓名
     */
    @NotBlank(message = "乘客姓名不能为空")
    private String passengerName;
    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull(message = "日期不能为空")
    private Date trainDate;
    /**
     * 车次编号
     */
    @NotBlank(message = "车次编号不能为空")
    private String trainCode;
    /**
     * 车厢索引
     */
    @NotNull(message = "车厢索引不能为空")
    private Integer carriageIndex;
    /**
     * 行号|01、02
     */
    @NotBlank(message = "行号不能为空")
    private String seatRow;
    /**
     * 列号|枚举[SeatColEnum]
     */
    @NotBlank(message = "列号不能为空")
    private String seatCol;
    /**
     * 出发站
     */
    @NotBlank(message = "出发站不能为空")
    private String startStation;
    /**
     * 出发时间
     */
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "出发时间不能为空")
    private Date startTime;
    /**
     * 终点站
     */
    @NotBlank(message = "终点站不能为空")
    private String endStation;
    /**
     * 到站时间
     */
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "到站时间不能为空")
    private Date endTime;
    /**
     * 座位类型|枚举[SeatTypeEnum]
     */
    @NotBlank(message = "座位类型不能为空")
    private String seatType;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "创建时间不能为空")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "更新时间不能为空")
    private Date updateTime;
}
