package com.github.blkcor.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainSeatSaveReq {
    /**
     * id
     */
    private Long id;
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
     * 新增时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
}
