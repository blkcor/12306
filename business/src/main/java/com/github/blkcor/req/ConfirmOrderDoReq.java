package com.github.blkcor.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmOrderDoReq {
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
     * 终点站
     */
    @NotBlank(message = "终点站不能为空")
    private String end;
    /**
     * 每日车次余票表id
     */
    @NotNull(message = "每日车次余票表id不能为空")
    private Long dailyTrainTicketId;
    /**
     * 购票信息
     */
    @NotEmpty(message = "购票信息不能为空")
    private List<ConfirmOrderTicketReq> tickets;
    /**
     * 验证码
     */
    @NotBlank(message = "图形验证码不能为空")
    private String verifyCode;
    /**
     * 验证码token
     */
    @NotBlank(message = "图形验证码token不能为空")
    private String verifyCodeToken;

    /**
     * 会员id
     */
    @NotNull(message = "会员id不能为空")
    private Long memberId;
}
