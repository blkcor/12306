package com.github.blkcor.mapper.custom;

import com.github.blkcor.entity.DailyTrainTicket;
import com.github.blkcor.entity.DailyTrainTicketExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DailyTrainTicketMapperCustom {
    void updateCountBySell(
            String seatTypeCode,
            String trainCode,
            Date date,
            Integer minStartIndex,
            Integer maxStartIndex,
            Integer minEndIndex,
            Integer maxEndIndex);
}