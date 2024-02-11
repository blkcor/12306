package com.github.blkcor.service;

import com.github.blkcor.entity.DailyTrainSeat;
import com.github.blkcor.entity.DailyTrainTicket;

import java.util.List;

public interface AfterConfirmOrderService {
    /**
     * 执行确认订单后的操作
     * @param finalSeatList 最终的座位列表
     */
    void afterDoConfirmOrder(DailyTrainTicket dailyTrainTicket, List<DailyTrainSeat> finalSeatList);
}
