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
public class DailyTrainSeatSaveReq {
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
     * 车厢序号
     */
    @NotNull(message = "车厢序号不能为空")
    private Integer carriageIndex;
    /**
     * 排号|01,02
     */
    @NotBlank(message = "排号不能为空")
    private String row;
    /**
     * 列号|枚举[SeatColEnum]
     */
    @NotBlank(message = "列号不能为空")
    private String col;
    /**
     * 座位类型|枚举[SeatTypeEnum]
     */
    @NotBlank(message = "座位类型不能为空")
    private String seatType;
    /**
     * 车厢座位序号
     */
    @NotNull(message = "车厢座位序号不能为空")
    private Integer carriageSeatIndex;
    /**
     * 售票状态|将经过的车站使用01来拼接，0代表未售，1代表已售
     */
    @NotBlank(message = "售票状态不能为空")
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
