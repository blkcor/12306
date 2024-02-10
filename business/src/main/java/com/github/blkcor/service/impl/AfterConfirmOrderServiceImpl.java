package com.github.blkcor.service.impl;

import com.github.blkcor.entity.DailyTrainSeat;
import com.github.blkcor.mapper.DailyTrainSeatMapper;
import com.github.blkcor.service.AfterConfirmOrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AfterConfirmOrderServiceImpl implements AfterConfirmOrderService {
    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;

    @Override
    @Transactional
    public void afterDoConfirmOrder(List<DailyTrainSeat> finalSeatList) {
            finalSeatList.forEach(seat -> {
                DailyTrainSeat dailyTrainSeatForUpdate = new DailyTrainSeat();
                dailyTrainSeatForUpdate.setId(seat.getId());
                dailyTrainSeatForUpdate.setSell(seat.getSell());
                dailyTrainSeatMapper.updateByPrimaryKeySelective(dailyTrainSeatForUpdate);
            });
    }


}
