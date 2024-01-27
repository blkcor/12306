package com.github.blkcor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SeatColEnum {
    YDZ_A("A", "A", "1"),
    YDZ_C("C", "C", "1"),
    YDZ_D("D", "D", "1"),
    YDZ_F("F", "F", "1"),
    EDZ_A("A", "A", "2"),
    EDZ_B("B", "B", "2"),
    EDZ_C("C", "C", "2"),
    EDZ_D("D", "D", "2"),
    EDZ_F("F", "F", "2");
    /**
     * 列代码
     */
    private String code;
    /**
     * 列描述
     */
    private String desc;
    /**
     * 对应SeatTypeEnum的code
     */
    private String seatType;

    /**
     * 通过座位类型获得列的代码，方便前端的展示，例如座位类型为一等座，那么列的代码为A、C、D、F可选
     * @param seatType 座位类型
     * @return 列代码列表
     */
    public List<SeatColEnum> getColsByType(String seatType) {
        List<SeatColEnum> cols = new ArrayList<>();
        for (SeatColEnum col : SeatColEnum.values()) {
            if (col.getSeatType().equals(seatType)) {
                cols.add(col);
            }
        }
        return cols;
    }
}
