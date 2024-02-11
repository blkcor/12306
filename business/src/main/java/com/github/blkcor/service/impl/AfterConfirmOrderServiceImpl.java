package com.github.blkcor.service.impl;

import com.github.blkcor.entity.DailyTrainSeat;
import com.github.blkcor.entity.DailyTrainTicket;
import com.github.blkcor.mapper.DailyTrainSeatMapper;
import com.github.blkcor.mapper.custom.DailyTrainTicketMapperCustom;
import com.github.blkcor.service.AfterConfirmOrderService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AfterConfirmOrderServiceImpl implements AfterConfirmOrderService {
    private final static Logger LOG = LoggerFactory.getLogger(AfterConfirmOrderServiceImpl.class);
    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;
    @Resource
    private DailyTrainTicketMapperCustom dailyTrainTicketMapperCustom;

    @Override
    @Transactional
    public void afterDoConfirmOrder(DailyTrainTicket dailyTrainTicket, List<DailyTrainSeat> finalSeatList) {
        Integer startIndex = dailyTrainTicket.getStartIndex();
        Integer endIndex = dailyTrainTicket.getEndIndex();
        finalSeatList.forEach(seat -> {
            DailyTrainSeat dailyTrainSeatForUpdate = new DailyTrainSeat();
            dailyTrainSeatForUpdate.setId(seat.getId());
            dailyTrainSeatForUpdate.setSell(seat.getSell());
            dailyTrainSeatMapper.updateByPrimaryKeySelective(dailyTrainSeatForUpdate);

            char[] sellInfo = dailyTrainSeatForUpdate.getSell().toCharArray();
            Integer maxStartIndex = endIndex - 1;
            Integer minStartIndex = 0;
            Integer minEndIndex = startIndex + 1;
            //计算min max的时候需要考虑好边界情况，可以在实际买票的时候进行测试调整
            for (int i = startIndex - 1; i >= 0; i--) {
                if (sellInfo[i] == '1') {
                    minStartIndex = i + 1;
                    break;
                }
            }
            LOG.info("影响的出发站区间:" + minStartIndex + "-" + maxStartIndex);
            Integer maxEndIndex = seat.getSeatType().length();
            for (int i = endIndex; i < sellInfo.length; i++) {
                if (sellInfo[i] == '1') {
                    maxEndIndex = i;
                    break;
                }
            }
            LOG.info("影响的到达站区间:" + minEndIndex + "-" + maxEndIndex);
            dailyTrainTicketMapperCustom.updateCountBySell(seat.getSeatType(), seat.getTrainCode(), seat.getDate(), minStartIndex, maxStartIndex, minEndIndex, maxEndIndex);
        });


    }


}
