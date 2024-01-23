package com.github.blkcor.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一结果返回对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResp<T> {
    /**
     * 成功或者失败
     */
    private boolean success = true;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回泛型数据，自定义类型
     */
    private T content;

    /**
     * 成功返回
     * @param content
     * @param <T>
     * @return
     */

    public static <T> CommonResp<T> success(T content) {
        return new CommonResp<>(true, null, content);
    }

    /**
     * 失败返回
     * @param message
     * @param <T>
     * @return
     */
    public static <T> CommonResp<T> fail(String message) {
        return new CommonResp<>(false, message, null);
    }


}
