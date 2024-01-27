package com.github.blkcor.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainCarriageSaveReq {
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
    private Integer index;
    /**
     * 座位类型|枚举[SeatTypeEnum]
     */
    @NotBlank(message = "座位类型不能为空")
    private String seatType;
    /**
     * 座位数量
     */
    @NotNull(message = "座位数量不能为空")
    private Integer seatCount;
    /**
     * 行数
     */
    @NotNull(message = "行数不能为空")
    private Integer rowCount;
    /**
     * 列数
     */
    @NotNull(message = "列数不能为空")
    private Integer columnCount;
    /**
     * 新增时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
}
