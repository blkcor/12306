package com.github.blkcor.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResp<T> {
    /**
     * 数据总条数
     */
    private Long total;

    /**
     * 数据列表
     */
    private List<T> list;
}
