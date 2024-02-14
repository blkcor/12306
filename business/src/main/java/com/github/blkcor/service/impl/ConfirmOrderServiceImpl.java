package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.*;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.github.blkcor.context.LoginMemberContext;
import com.github.blkcor.entity.*;
import com.github.blkcor.enums.ConfirmOrderStatusEnum;
import com.github.blkcor.enums.SeatColEnum;
import com.github.blkcor.enums.SeatTypeEnum;
import com.github.blkcor.exception.BusinessException;
import com.github.blkcor.exception.BusinessExceptionEnum;
import com.github.blkcor.mapper.ConfirmOrderMapper;
import com.github.blkcor.mapper.DailyTrainTicketMapper;
import com.github.blkcor.req.ConfirmOrderQueryReq;
import com.github.blkcor.req.ConfirmOrderDoReq;
import com.github.blkcor.req.ConfirmOrderTicketReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.ConfirmOrderQueryResp;
import com.github.blkcor.service.AfterConfirmOrderService;
import com.github.blkcor.service.ConfirmOrderService;
import com.github.blkcor.service.DailyTrainCarriageService;
import com.github.blkcor.service.DailyTrainSeatService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class ConfirmOrderServiceImpl implements ConfirmOrderService {
    private static final Logger LOG = LoggerFactory.getLogger(ConfirmOrderServiceImpl.class);
    @Resource
    private ConfirmOrderMapper confirmOrderMapper;
    @Resource
    private DailyTrainTicketMapper dailyTrainTicketMapper;
    @Resource
    private DailyTrainCarriageService dailyTrainCarriageService;
    @Resource
    private DailyTrainSeatService dailyTrainSeatService;
    @Resource
    private AfterConfirmOrderService afterConfirmOrderService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

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
        //（省略）数据校验，车次是否存在，车次余票存在，车次是否在有效期内，ticket条数>0，同z同车次同日期不能重复
        String lockKey = confirmOrderSaveReq.getTrainCode() + "-" + confirmOrderSaveReq.getDate();
        Boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, lockKey, 5, TimeUnit.SECONDS);
        if (Boolean.TRUE.equals(locked)) {
            LOG.info("获取锁成功，可以执行购票");
        } else {
            LOG.error("获取锁失败，购票失败");
            throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_LOCK_FAIL);
        }
        try {
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
            //最终的选座结果
            List<DailyTrainSeat> finalSeatList = new ArrayList<>();
            //判断是否有选座
            if (StrUtil.isBlank(confirmOrderSaveReq.getTickets().get(0).getSeat())) {
                LOG.info("本次购票未选座");
                //系统选座，需要一个个的选
                confirmOrderSaveReq.getTickets().forEach(ticket -> {
                    getSeat(finalSeatList, dailyTrainTicket, confirmOrderSaveReq.getTrainCode(), confirmOrderSaveReq.getDate(), ticket.getSeatTypeCode(), null, null);
                });
            } else {
                LOG.info("本次购票选座");
                List<SeatColEnum> colEnumList = SeatColEnum.getColsByType(confirmOrderSaveReq.getTickets().get(0).getSeatTypeCode());
                LOG.info("本次购票选座列信息：{}", JSONUtil.toJsonStr(colEnumList));
                //组成A1,C1,D1,F1,A2,C2,D2,F2这种格式的座位信息，只生成两排座位信息
                List<String> referSeatList = new ArrayList<>();
                for (int i = 1; i <= 2; i++) {
                    for (SeatColEnum seatColEnum : colEnumList) {
                        referSeatList.add(seatColEnum.getCode() + i);
                    }
                }
                LOG.info("本次购票参照座位信息：{}", JSONUtil.toJsonStr(referSeatList));
                //获取偏移值数组
                List<Integer> offsetList = new ArrayList<>();
                referSeatList.stream().filter(confirmOrderSaveReq.getTickets().stream().map(ConfirmOrderTicketReq::getSeat).toList()::contains).map(referSeatList::indexOf).forEach(offsetList::add);
                List<Integer> normalizedOffsetList = offsetList.stream().map(position -> position - offsetList.get(0)).toList();
                LOG.info("计算得到座位的偏移值：{}", JSONUtil.toJsonStr(normalizedOffsetList));
                //4.1、按车厢一个一个获取座位信息
                //4.2、判断座位是否可用（是否被买过，是否满足乘客的选座要求，多个选座应该在同一个车厢）
                getSeat(finalSeatList, dailyTrainTicket, confirmOrderSaveReq.getTrainCode(), confirmOrderSaveReq.getDate(), confirmOrderSaveReq.getTickets().get(0).getSeatTypeCode(), confirmOrderSaveReq.getTickets().get(0).getSeat().substring(0, 1), normalizedOffsetList);
            }
            LOG.info("最终选中的座位：{}", JSONUtil.toJsonStr(finalSeatList));
            //5、事务处理
            //5.1、座位表修改售卖情况
            //5.2、余票表修改库存
            //5.3、为会员增加购票记录
            //5.4、更新订单表状态为成功
            afterConfirmOrderService.afterDoConfirmOrder(confirmOrderSaveReq, dailyTrainTicket, finalSeatList, confirmOrder);
            return CommonResp.success(null);
        } catch (Exception e) {
            LOG.error("保存购票信息异常");
            throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_EXCEPTION);
        } finally {
            LOG.info("购票结束，释放锁");
            stringRedisTemplate.delete(lockKey);
        }
    }

    public void getSeat(List<DailyTrainSeat> finalSeatList, DailyTrainTicket dailyTrainTicket, String trainCode, Date date, String seatType, String colType, List<Integer> offsetList) {
        //4.1、按车厢一个一个获取座位信息
        List<DailyTrainCarriage> carriageList = dailyTrainCarriageService.selectBySeatType(trainCode, date, seatType);
        List<DailyTrainSeat> getSeatList;
        LOG.info("共查出{}个符合条件的车厢", carriageList.size());
        for (DailyTrainCarriage carriage : carriageList) {
            getSeatList = new ArrayList<>();
            LOG.info("开始从车厢{}寻找座位", carriage.getIndex());
            List<DailyTrainSeat> trainSeatList = dailyTrainSeatService.selectByCarriageIndex(trainCode, date, carriage.getIndex());
            LOG.info("车厢{}共查出{}个座位", carriage.getIndex(), trainSeatList.size());
            for (DailyTrainSeat trainSeat : trainSeatList) {
                //判断座位是否重复选中
                if (finalSeatList.stream().map(DailyTrainSeat::getId).toList().contains(trainSeat.getId())) {
                    LOG.info("座位{}已被重复选中，跳过该座位", trainSeat.getCarriageSeatIndex());
                    continue;
                }
                //如果colType有值，表示是选座，先判断列号是否满足
                if (StrUtil.isNotBlank(colType)) {
                    if (!trainSeat.getCol().equals(colType)) {
                        LOG.info("座位{}列号{}不满足要求{}", trainSeat.getCarriageSeatIndex(), trainSeat.getCol(), colType);
                        continue;
                    }
                }
                Boolean canSell = canSell(trainSeat, dailyTrainTicket.getStartIndex(), dailyTrainTicket.getEndIndex());
                if (canSell) {
                    LOG.info("选中座位：{}", trainSeat);
                    getSeatList.add(trainSeat);
                } else {
                    LOG.info("未选中座位：{}，当前座位不可售", trainSeat);
                    continue;
                }
                boolean isGetAllOffsetSeat = true;
                //根据offsetList判断选中的其他座位是否可售
                if (CollUtil.isNotEmpty(offsetList)) {
                    LOG.info("有偏移值{},开始判断其他座位是否可售", offsetList);
                    for (Integer offset : offsetList) {
                        Integer seatIndex = trainSeat.getCarriageSeatIndex();
                        int nextIndex = seatIndex + offset - 1;
                        //判断偏移值是否合法
                        if (nextIndex > trainSeatList.size()) {
                            LOG.info("偏移值{}超出车厢座位数量{}", offset, trainSeatList.size());
                            isGetAllOffsetSeat = false;
                            break;
                        }
                        //判断是否可选
                        DailyTrainSeat nextSeat = trainSeatList.get(nextIndex);
                        if (canSell(nextSeat, dailyTrainTicket.getStartIndex(), dailyTrainTicket.getEndIndex())) {
                            LOG.info("选中座位：{}", nextSeat.getCarriageSeatIndex());
                            getSeatList.add(nextSeat);
                        } else {
                            LOG.info("未选中座位：{}，当前座位不可售", nextSeat.getCarriageSeatIndex());
                            isGetAllOffsetSeat = false;
                            break;
                        }
                    }
                }
                //从当前车厢下一个座位开始进行判断
                if (!isGetAllOffsetSeat) {
                    getSeatList.clear();
                    continue;
                }
                //都选中了，我们需要保存选好的座位
                finalSeatList.addAll(getSeatList);
                return;
            }
        }
    }

    /**
     * 判断某个座位是否可卖
     *
     * @param dailyTrainSeat 每日车次座位信息
     */
    private Boolean canSell(DailyTrainSeat dailyTrainSeat, Integer startIndex, Integer endIndex) {
        String sell = dailyTrainSeat.getSell();
        String sellPart = sell.substring(startIndex, endIndex);
        //如果sellPart部分包含1，则表示有座位被卖了，此时不可售
        if (Integer.parseInt(sellPart) > 0) {
            LOG.info("座位{}在{}-{}区间已售", dailyTrainSeat.getCarriageSeatIndex(), startIndex, endIndex);
            return false;
        } else {
            LOG.info("未售过票，可选中该座位");
            String curSell = sellPart.replace('0', '1');
            //用0填充成原来的长度
            curSell = StrUtil.fillBefore(curSell, '0', endIndex);
            curSell = StrUtil.fillAfter(curSell, '0', sell.length());
            //当前售卖信息和数据库中的已售信息进行按位或，得到最终的结果
            int newSellInt = NumberUtil.binaryToInt(curSell) | NumberUtil.binaryToInt(sell);
            //to binary str
            String finalSell = NumberUtil.getBinaryStr(newSellInt);
            //fill again
            finalSell = StrUtil.fillBefore(finalSell, '0', sell.length());
            LOG.info("座位{}被选中，原售票信息为:{}, 车站区间{}~{}，即：{},最终售票信息为:{}", dailyTrainSeat.getCarriageSeatIndex(), sell, startIndex, endIndex, sellPart, finalSell);
            dailyTrainSeat.setSell(finalSell);
            return true;
        }
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