package com.github.blkcor.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TrainStationQueryReq extends PageReq {
    /**
     * 车次编号
     */
    private String code;
}
