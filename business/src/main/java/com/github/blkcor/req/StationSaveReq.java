package com.github.blkcor.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationSaveReq {
    /**
     * id
     */
    private Long id;
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
     * 站名拼音首字母
     */
    @NotBlank(message = "站名拼音首字母不能为空")
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
