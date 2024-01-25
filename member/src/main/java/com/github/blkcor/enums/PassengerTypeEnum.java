package com.github.blkcor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PassengerTypeEnum {
    AUDIT("1","成人"),

    CHILD("2","儿童"),

    STUDENT("3","学生");

    private String code;

    private String desc;

}
