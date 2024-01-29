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
public class DailyTrainStationSaveReq {
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
     * 站序号
     */
    @NotNull(message = "站序号不能为空")
    private Integer index;
    /**
     * 站名
     */
    @NotBlank(message = "站名不能为空")
    private String name;
    /**
     * 站名拼音
     */
    @NotBlank(message = "站名拼音不能为空")
    private String namePinyin;
    /**
     * 进站时间
     */
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "进站时间不能为空")
    private Date inTime;
    /**
     * 出站时间
     */
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "出站时间不能为空")
    private Date outTime;
    /**
     * 停留时间
     */
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "停留时间不能为空")
    private Date stopTime;
    /**
     * 公里数|从上一站到本站的距离
     */
    @NotBlank(message = "公里数不能为空")
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
