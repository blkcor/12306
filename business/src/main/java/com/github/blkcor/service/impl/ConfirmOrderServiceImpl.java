package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.github.blkcor.context.LoginMemberContext;
import com.github.blkcor.entity.ConfirmOrder;
import com.github.blkcor.entity.ConfirmOrderExample;
import com.github.blkcor.entity.DailyTrainTicket;
import com.github.blkcor.entity.DailyTrainTicketExample;
import com.github.blkcor.enums.ConfirmOrderStatusEnum;
import com.github.blkcor.enums.SeatTypeEnum;
import com.github.blkcor.exception.BusinessException;
import com.github.blkcor.exception.BusinessExceptionEnum;
import com.github.blkcor.mapper.ConfirmOrderMapper;
import com.github.blkcor.mapper.DailyTrainTicketMapper;
import com.github.blkcor.req.ConfirmOrderQueryReq;
import com.github.blkcor.req.ConfirmOrderDoReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.ConfirmOrderQueryResp;
import com.github.blkcor.service.ConfirmOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConfirmOrderServiceImpl implements ConfirmOrderService {
    private static final Logger LOG = LoggerFactory.getLogger(ConfirmOrderServiceImpl.class);
    @Resource
    private ConfirmOrderMapper confirmOrderMapper;
    @Resource
    private DailyTrainTicketMapper dailyTrainTicketMapper;

    @Override
    public CommonResp<Void> saveConfirmOrder(ConfirmOrderDoReq confirmOrderSaveReq) {
        ConfirmOrder confirmOrder = BeanUtil.copyProperties(confirmOrderSaveReq, ConfirmOrder.class);
        if (ObjectUtil.isNull(confirmOrder.getId())) {
            confirmOrder.setCreateTime(DateTime.now());
            confirmOrder.setUpdateTime(DateTime.now());
            confirmOrder.setId(IdUtil.getSnowflake(1, 1).nextId());
            confirmOrderMapper.insertSelective(confirmOrder);
        } else {
            confirmOrder.setUpdateTime(DateTime.now());
            confirmOrderMapper.updateByPrimaryKeySelective(confirmOrder);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<ConfirmOrderQueryResp>> queryConfirmOrderList(ConfirmOrderQueryReq confirmOrderQueryReq) {
        ConfirmOrderExample confirmOrderExample = new ConfirmOrderExample();
        ConfirmOrderExample.Criteria criteria = confirmOrderExample.createCriteria();

        LOG.info("查询页码：{}", confirmOrderQueryReq.getPage());
        LOG.info("查询条数：{}", confirmOrderQueryReq.getSize());

        PageHelper.startPage(confirmOrderQueryReq.getPage(), confirmOrderQueryReq.getSize());
        List<ConfirmOrder> confirmOrders = confirmOrderMapper.selectByExample(confirmOrderExample);
        PageInfo<ConfirmOrder> pageInfo = new PageInfo<>(confirmOrders);

        LOG.info("总条数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<ConfirmOrderQueryResp> list = BeanUtil.copyToList(confirmOrders, ConfirmOrderQueryResp.class);
        PageResp<ConfirmOrderQueryResp> confirmOrderQueryRespPageResp = new PageResp<>();
        confirmOrderQueryRespPageResp.setList(list);
        confirmOrderQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(confirmOrderQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteConfirmOrder(Long id) {
        confirmOrderMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<Void> doConfirmOrder(ConfirmOrderDoReq confirmOrderSaveReq) {
        //（省略）数据校验，车次是否存在，车次余票存在，车次是否在有效期内，ticket条数>0，同乘客同车次同日期不能重复
        //1、保存确认订单表，状态初始化
        ConfirmOrder confirmOrder = new ConfirmOrder();
        confirmOrder.setId(IdUtil.getSnowflake(1, 1).nextId());
        confirmOrder.setMemberId(LoginMemberContext.getId());
        confirmOrder.setDate(confirmOrderSaveReq.getDate());
        confirmOrder.setTrainCode(confirmOrderSaveReq.getTrainCode());
        confirmOrder.setStart(confirmOrderSaveReq.getStart());
        confirmOrder.setEnd(confirmOrderSaveReq.getEnd());
        confirmOrder.setDailyTrainTicketId(confirmOrderSaveReq.getDailyTrainTicketId());
        confirmOrder.setStatus(ConfirmOrderStatusEnum.INIT.getCode());
        confirmOrder.setCreateTime(DateTime.now());
        confirmOrder.setUpdateTime(DateTime.now());
        confirmOrder.setTickets(JSON.toJSONString(confirmOrderSaveReq.getTickets()));
        confirmOrderMapper.insertSelective(confirmOrder);
        //2、查处余票记录，得到真实的库存
        DailyTrainTicketExample dailyTrainTicketExample = new DailyTrainTicketExample();
        dailyTrainTicketExample.createCriteria().andDateEqualTo(confirmOrderSaveReq.getDate()).andTrainCodeEqualTo(confirmOrderSaveReq.getTrainCode()).andStartEqualTo(confirmOrderSaveReq.getStart()).andEndEqualTo(confirmOrderSaveReq.getEnd());
        DailyTrainTicket dailyTrainTicket = dailyTrainTicketMapper.selectByExample(dailyTrainTicketExample).get(0);
        if (ObjectUtil.isNull(dailyTrainTicket)) {
            LOG.error("车次{}余票信息不存在", confirmOrderSaveReq.getTrainCode());
            return CommonResp.fail("余票信息不存在");
        }
        LOG.info("车次{}余票信息：{}", confirmOrderSaveReq.getTrainCode(), JSONUtil.toJsonStr(dailyTrainTicket));
        //3、扣减余票数量，判断余票是否足够
        reduceTicketStore(confirmOrderSaveReq, dailyTrainTicket);
        //4、选座
        //4.1、按车厢一个一个获取座位信息

        //4.2、判断座位是否可用（是否被买过，是否满足乘客的选座要求，多个选座应该在同一个车厢）

        //5、事务处理
        //5.1、座位表修改售卖情况
        //5.2、余票表修改库存
        //5.3、为会员增加购票记录
        //5.4、更新订单表状态为成功

        return null;
    }

    private static void reduceTicketStore(ConfirmOrderDoReq confirmOrderSaveReq, DailyTrainTicket dailyTrainTicket) {
        confirmOrderSaveReq.getTickets().forEach(ticket -> {
            String seatTypeCode = ticket.getSeatTypeCode();
            SeatTypeEnum seatTypeEnum = EnumUtil.getBy(SeatTypeEnum::getCode, seatTypeCode);
            switch (seatTypeEnum) {
                case YDZ -> {
                    int ticketLeft = dailyTrainTicket.getYdz() - 1;
                    if (ticketLeft < 0) {
                        LOG.error("车次{}一等座余票不足", confirmOrderSaveReq.getTrainCode());
                        throw new BusinessException(BusinessExceptionEnum.BUSINESS_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setYdz(ticketLeft);
                }
                case EDZ -> {
                    int ticketLeft = dailyTrainTicket.getEdz() - 1;
                    if (ticketLeft < 0) {
                        LOG.error("车次{}二等座余票不足", confirmOrderSaveReq.getTrainCode());
                        throw new BusinessException(BusinessExceptionEnum.BUSINESS_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setEdz(ticketLeft);
                }
                case RW -> {
                    int ticketLeft = dailyTrainTicket.getRw() - 1;
                    if (ticketLeft < 0) {
                        LOG.error("车次{}软卧余票不足", confirmOrderSaveReq.getTrainCode());
                        throw new BusinessException(BusinessExceptionEnum.BUSINESS_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setRw(ticketLeft);
                }
                case YW -> {
                    int ticketLeft = dailyTrainTicket.getYw() - 1;
                    if (ticketLeft < 0) {
                        LOG.error("车次{}硬卧余票不足", confirmOrderSaveReq.getTrainCode());
                        throw new BusinessException(BusinessExceptionEnum.BUSINESS_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setYw(ticketLeft);
                }
            }
        });
    }
}