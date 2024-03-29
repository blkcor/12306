package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.*;
import com.github.blkcor.enums.SeatTypeEnum;
import com.github.blkcor.enums.TrainTypeEnum;
import com.github.blkcor.mapper.DailyTrainTicketMapper;
import com.github.blkcor.mapper.TrainMapper;
import com.github.blkcor.mapper.TrainStationMapper;
import com.github.blkcor.req.DailyTrainTicketQueryReq;
import com.github.blkcor.req.DailyTrainTicketSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainTicketQueryResp;
import com.github.blkcor.service.DailyTrainSeatService;
import com.github.blkcor.service.DailyTrainTicketService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service
public class DailyTrainTicketServiceImpl implements DailyTrainTicketService {
    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainTicketServiceImpl.class);
    @Resource
    private DailyTrainTicketMapper dailyTrainTicketMapper;
    @Resource
    private TrainStationMapper trainStationMapper;
    @Resource
    private DailyTrainSeatService dailyTrainSeatService;
    @Resource
    private TrainMapper trainMapper;

    @Override
    public CommonResp<Void> saveDailyTrainTicket(DailyTrainTicketSaveReq dailyTrainTicketSaveReq) {
        DailyTrainTicket dailyTrainTicket = BeanUtil.copyProperties(dailyTrainTicketSaveReq, DailyTrainTicket.class);
        if (ObjectUtil.isNull(dailyTrainTicket.getId())) {
            dailyTrainTicket.setCreateTime(DateTime.now());
            dailyTrainTicket.setUpdateTime(DateTime.now());
            dailyTrainTicket.setId(IdUtil.getSnowflake(1, 1).nextId());
            dailyTrainTicketMapper.insertSelective(dailyTrainTicket);
        } else {
            dailyTrainTicket.setUpdateTime(DateTime.now());
            dailyTrainTicketMapper.updateByPrimaryKeySelective(dailyTrainTicket);
        }
        return CommonResp.success(null);
    }

    @Cacheable("DailyTrainTicketService.queryDailyTrainTicketList")
    @Override
    public CommonResp<PageResp<DailyTrainTicketQueryResp>> queryDailyTrainTicketList(DailyTrainTicketQueryReq dailyTrainTicketQueryReq) {
        DailyTrainTicketExample dailyTrainTicketExample = new DailyTrainTicketExample();
        dailyTrainTicketExample.setOrderByClause("date asc, train_code asc");
        DailyTrainTicketExample.Criteria criteria = dailyTrainTicketExample.createCriteria();
        if (ObjectUtil.isNotNull(dailyTrainTicketQueryReq.getDate())) {
            criteria.andDateEqualTo(dailyTrainTicketQueryReq.getDate());
        }
        if (ObjectUtil.isNotNull(dailyTrainTicketQueryReq.getTrainCode())) {
            criteria.andTrainCodeEqualTo(dailyTrainTicketQueryReq.getTrainCode());
        }
        if (ObjectUtil.isNotNull(dailyTrainTicketQueryReq.getStartStation())) {
            criteria.andStartEqualTo(dailyTrainTicketQueryReq.getStartStation());
        }
        if (ObjectUtil.isNotNull(dailyTrainTicketQueryReq.getEndStation())) {
            criteria.andEndEqualTo(dailyTrainTicketQueryReq.getEndStation());
        }

        LOG.info("查询页码：{}", dailyTrainTicketQueryReq.getPage());
        LOG.info("查询条数：{}", dailyTrainTicketQueryReq.getSize());

        PageHelper.startPage(dailyTrainTicketQueryReq.getPage(), dailyTrainTicketQueryReq.getSize());
        List<DailyTrainTicket> dailyTrainTickets = dailyTrainTicketMapper.selectByExample(dailyTrainTicketExample);
        PageInfo<DailyTrainTicket> pageInfo = new PageInfo<>(dailyTrainTickets);

        LOG.info("总条数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainTicketQueryResp> list = BeanUtil.copyToList(dailyTrainTickets, DailyTrainTicketQueryResp.class);
        PageResp<DailyTrainTicketQueryResp> dailyTrainTicketQueryRespPageResp = new PageResp<>();
        dailyTrainTicketQueryRespPageResp.setList(list);
        dailyTrainTicketQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(dailyTrainTicketQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteDailyTrainTicket(Long id) {
        dailyTrainTicketMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<Void> genDailyTrainTicket(Date date, String code) {
        LOG.info("生成每日车次余票信息，日期：{}，车次：{}，任务开始", date, code);
        //删除当天的车次余票信息
        DailyTrainTicketExample dailyTrainTicketExample = new DailyTrainTicketExample();
        dailyTrainTicketExample.createCriteria().andDateEqualTo(date).andTrainCodeEqualTo(code);
        dailyTrainTicketMapper.deleteByExample(dailyTrainTicketExample);
        //查询车次的车站信息
        TrainStationExample trainStationExample = new TrainStationExample();
        trainStationExample.createCriteria().andTrainCodeEqualTo(code);
        List<TrainStation> trainStationList = trainStationMapper.selectByExample(trainStationExample);
        //查询车次类型
        TrainExample trainExample = new TrainExample();
        trainExample.createCriteria().andCodeEqualTo(code);
        List<Train> trainList = trainMapper.selectByExample(trainExample);
        if (CollUtil.isEmpty(trainList)) {
            LOG.info("车次{}没有找到对应的车次信息", code);
            return CommonResp.fail("没有找到对应的车次信息");
        }
        String trainType = trainList.get(0).getType();
        BigDecimal rate = EnumUtil.getFieldBy(TrainTypeEnum::getPriceRate, TrainTypeEnum::getCode, trainType);
        if (CollUtil.isEmpty(trainStationList)) {
            LOG.info("车次{}没有找到对应的车站信息", code);
            return CommonResp.fail("没有找到车次对应的车站信息");
        }
        for (int i = 0; i < trainStationList.size(); i++) {
            TrainStation trainStationStart = trainStationList.get(i);
            BigDecimal distance = BigDecimal.ZERO;
            for (int j = i + 1; j < trainStationList.size(); j++) {
                TrainStation trainStationEnd = trainStationList.get(j);
                //计算两站之间的距离
                distance = distance.add(trainStationEnd.getKm());
                //生成车次余票信息
                DailyTrainTicket dailyTrainTicket = new DailyTrainTicket();
                dailyTrainTicket.setId(IdUtil.getSnowflake(1, 1).nextId());
                dailyTrainTicket.setDate(date);
                dailyTrainTicket.setTrainCode(code);
                dailyTrainTicket.setStart(trainStationStart.getName());
                dailyTrainTicket.setStartPinyin(trainStationStart.getNamePinyin());
                dailyTrainTicket.setStartTime(trainStationStart.getOutTime());
                dailyTrainTicket.setStartIndex(trainStationStart.getIndex());
                dailyTrainTicket.setEnd(trainStationEnd.getName());
                dailyTrainTicket.setEndPinyin(trainStationEnd.getNamePinyin());
                dailyTrainTicket.setEndTime(trainStationEnd.getInTime());
                dailyTrainTicket.setEndIndex(trainStationEnd.getIndex());
                dailyTrainTicket.setYdz(dailyTrainSeatService.countSeat(date, code, "1").intValue());
                dailyTrainTicket.setYdzPrice(rate.multiply(SeatTypeEnum.YDZ.getPrice()).multiply(distance));
                dailyTrainTicket.setEdz(dailyTrainSeatService.countSeat(date, code, "2").intValue());
                dailyTrainTicket.setEdzPrice(rate.multiply(SeatTypeEnum.EDZ.getPrice()).multiply(distance));
                dailyTrainTicket.setRw(dailyTrainSeatService.countSeat(date, code, "3").intValue());
                dailyTrainTicket.setRwPrice(rate.multiply(SeatTypeEnum.RW.getPrice()).multiply(distance));
                dailyTrainTicket.setYw(dailyTrainSeatService.countSeat(date, code, "4").intValue());
                dailyTrainTicket.setYwPrice(rate.multiply(SeatTypeEnum.YW.getPrice()).multiply(distance));
                dailyTrainTicket.setCreateTime(DateTime.now());
                dailyTrainTicket.setUpdateTime(DateTime.now());
            }
        }

        LOG.info("生成每日车次余票信息，日期：{}，车次：{}，任务结束", date, code);
        return null;
    }
}