package com.github.blkcor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmOrderDto {
    /**
     * 日期
     */
    private Date date;

    /**
     * 车次编号
     */
    private String trainCode;
}
