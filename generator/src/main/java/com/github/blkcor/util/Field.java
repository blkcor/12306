package com.github.blkcor.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Field {
    /**
     * 字段名
     */
    private String name;
    /**
     * 字段名小驼峰
     */
    private String nameHump;
    /**
     * 字段名大驼峰
     */
    private String nameBigHump;
    /**
     * 字段名中文
     */
    private String nameCn;
    /**
     * 字段类型
     */
    private String type;
    /**
     * java类型
     */
    private String javaType;
    /**
     * 注释
     */
    private String comment;
    /**
     * 是否可空
     */
    private Boolean nullable;
    /**
     * 字段长度
     */
    private Integer length;
    /**
     * 是否是枚举
     */
    private Boolean enums;
    /**
     * 枚举常量
     */
    private String enumsConst;
}
