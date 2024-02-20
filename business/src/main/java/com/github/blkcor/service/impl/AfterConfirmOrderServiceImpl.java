package com.github.blkcor.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import com.github.blkcor.entity.ConfirmOrder;
import com.github.blkcor.entity.DailyTrainSeat;
import com.github.blkcor.entity.DailyTrainTicket;
import com.github.blkcor.enums.ConfirmOrderStatusEnum;
import com.github.blkcor.feign.MemberFeign;
import com.github.blkcor.mapper.ConfirmOrderMapper;
import com.github.blkcor.mapper.DailyTrainSeatMapper;
import com.github.blkcor.mapper.custom.DailyTrainTicketMapperCustom;
import com.github.blkcor.req.ConfirmOrderDoReq;
import com.github.blkcor.req.MemberTicketSaveReq;
import com.github.blkcor.service.AfterConfirmOrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AfterConfirmOrderServiceImpl implements AfterConfirmOrderService {
    private final static Logger LOG = LoggerFactory.getLogger(AfterConfirmOrderServiceImpl.class);
    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;
    @Resource
    private DailyTrainTicketMapperCustom dailyTrainTicketMapperCustom;
    @Resource
    private MemberFeign memberFeign;
    @Resource
    private ConfirmOrderMapper confirmOrderMapper;

    @Override
    @GlobalTransactional
    public void afterDoConfirmOrder(ConfirmOrderDoReq confirmOrderSaveReq, DailyTrainTicket dailyTrainTicket, List<DailyTrainSeat> finalSeatList, ConfirmOrder confirmOrder) {
        LOG.info("全局事务id：{}", RootContext.getXID());
        Integer startIndex = dailyTrainTicket.getStartIndex();
        Integer endIndex = dailyTrainTicket.getEndIndex();
        for (int j = 0; j < finalSeatList.size(); j++) {
            DailyTrainSeat seat = finalSeatList.get(j);
            DailyTrainSeat dailyTrainSeatForUpdate = new DailyTrainSeat();
            dailyTrainSeatForUpdate.setId(seat.getId());
            dailyTrainSeatForUpdate.setSell(seat.getSell());
            dailyTrainSeatMapper.updateByPrimaryKeySelective(dailyTrainSeatForUpdate);

            char[] sellInfo = dailyTrainSeatForUpdate.getSell().toCharArray();
            int maxStartIndex = endIndex - 1;
            int minStartIndex = 0;
            int minEndIndex = startIndex + 1;
            //计算min max的时候需要考虑好边界情况，可以在实际买票的时候进行测试调整
            for (int i = startIndex - 1; i >= 0; i--) {
                if (sellInfo[i] == '1') {
                    minStartIndex = i + 1;
                    break;
                }
            }
            LOG.info("影响的出发站区间:" + minStartIndex + "-" + maxStartIndex);
            int maxEndIndex = seat.getSeatType().length();
            for (int i = endIndex; i < sellInfo.length; i++) {
                if (sellInfo[i] == '1') {
                    maxEndIndex = i;
                    break;
                }
            }
            LOG.info("影响的到达站区间:" + minEndIndex + "-" + maxEndIndex);
            dailyTrainTicketMapperCustom.updateCountBySell(seat.getSeatType(), seat.getTrainCode(), seat.getDate(), minStartIndex, maxStartIndex, minEndIndex, maxEndIndex);
            MemberTicketSaveReq memberTicketSaveReq = new MemberTicketSaveReq();
            memberTicketSaveReq.setId(IdUtil.getSnowflake(1, 1).nextId());
            memberTicketSaveReq.setMemberId(confirmOrderSaveReq.getMemberId());
            memberTicketSaveReq.setPassengerId(confirmOrderSaveReq.getTickets().get(j).getPassengerId());
            memberTicketSaveReq.setPassengerName(confirmOrderSaveReq.getTickets().get(j).getPassengerName());
            memberTicketSaveReq.setTrainDate(seat.getDate());
            memberTicketSaveReq.setTrainCode(seat.getTrainCode());
            memberTicketSaveReq.setCarriageIndex(seat.getCarriageIndex());
            memberTicketSaveReq.setSeatRow(seat.getRow());
            memberTicketSaveReq.setSeatCol(seat.getCol());
            memberTicketSaveReq.setStartStation(dailyTrainTicket.getStart());
            memberTicketSaveReq.setStartTime(dailyTrainTicket.getStartTime());
            memberTicketSaveReq.setEndStation(dailyTrainTicket.getEnd());
            memberTicketSaveReq.setEndTime(dailyTrainTicket.getEndTime());
            memberTicketSaveReq.setSeatType(seat.getSeatType());
            memberTicketSaveReq.setCreateTime(DateTime.now());
            memberTicketSaveReq.setUpdateTime(DateTime.now());
            //保存会员车票信息
            memberFeign.save(memberTicketSaveReq);
        }
        ConfirmOrder confirmOrderForUpdate = new ConfirmOrder();
        confirmOrderForUpdate.setStatus(ConfirmOrderStatusEnum.SUCCESS.getCode());
        confirmOrderForUpdate.setUpdateTime(DateTime.now());
        confirmOrderForUpdate.setId(confirmOrder.getId());
        confirmOrderMapper.updateByPrimaryKeySelective(confirmOrderForUpdate);
    }
}
