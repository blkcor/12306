package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.*;
import com.github.blkcor.mapper.DailyTrainMapper;
import com.github.blkcor.mapper.TrainMapper;
import com.github.blkcor.req.DailyTrainQueryReq;
import com.github.blkcor.req.DailyTrainSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainQueryResp;
import com.github.blkcor.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class DailyTrainServiceImpl implements DailyTrainService {
    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainServiceImpl.class);
    @Resource
    private DailyTrainMapper dailyTrainMapper;
    @Resource
    private TrainMapper trainMapper;
    @Resource
    private DailyTrainStationService dailyTrainStationService;
    @Resource
    private DailyTrainCarriageService dailyTrainCarriageService;
    @Resource
    private DailyTrainSeatService dailyTrainSeatService;
    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    @Override
    public CommonResp<Void> saveDailyTrain(DailyTrainSaveReq dailyTrainSaveReq) {
        DailyTrain dailyTrain = BeanUtil.copyProperties(dailyTrainSaveReq, DailyTrain.class);
        if (ObjectUtil.isNull(dailyTrain.getId())) {
            dailyTrain.setCreateTime(DateTime.now());
            dailyTrain.setUpdateTime(DateTime.now());
            dailyTrain.setId(IdUtil.getSnowflake(1, 1).nextId());
            dailyTrainMapper.insertSelective(dailyTrain);
        } else {
            dailyTrain.setUpdateTime(DateTime.now());
            dailyTrainMapper.updateByPrimaryKeySelective(dailyTrain);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<DailyTrainQueryResp>> queryDailyTrainList(DailyTrainQueryReq dailyTrainQueryReq) {
        DailyTrainExample dailyTrainExample = new DailyTrainExample();
        //按日期倒序，车次升序排列
        dailyTrainExample.setOrderByClause("date desc, code asc");
        DailyTrainExample.Criteria criteria = dailyTrainExample.createCriteria();
        //增加选择查询条件
        if (ObjectUtil.isNotEmpty(dailyTrainQueryReq.getTrainCode())) {
            criteria.andCodeEqualTo(dailyTrainQueryReq.getTrainCode());
        }

        if (ObjectUtil.isNotEmpty(dailyTrainQueryReq.getDate())) {
            criteria.andDateEqualTo(dailyTrainQueryReq.getDate());
        }

        LOG.info("查询页码：{}", dailyTrainQueryReq.getPage());
        LOG.info("查询条数：{}", dailyTrainQueryReq.getSize());

        PageHelper.startPage(dailyTrainQueryReq.getPage(), dailyTrainQueryReq.getSize());
        List<DailyTrain> dailyTrains = dailyTrainMapper.selectByExample(dailyTrainExample);
        PageInfo<DailyTrain> pageInfo = new PageInfo<>(dailyTrains);

        LOG.info("总条数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainQueryResp> list = BeanUtil.copyToList(dailyTrains, DailyTrainQueryResp.class);
        PageResp<DailyTrainQueryResp> dailyTrainQueryRespPageResp = new PageResp<>();
        dailyTrainQueryRespPageResp.setList(list);
        dailyTrainQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(dailyTrainQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteDailyTrain(Long id) {
        dailyTrainMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<Void> genDaily(Date date) {
        LOG.info("生成{}的每日车次信息，任务开始", date);
        //查询所有的车次信息
        TrainExample trainExample = new TrainExample();
        trainExample.setOrderByClause("code asc");
        List<Train> trainList = trainMapper.selectByExample(trainExample);
        if (CollUtil.isEmpty(trainList)) {
            LOG.info("没有车次信息，任务结束");
            return CommonResp.success(null);
        }
        //生成车次，车厢，座位信息
        trainList.forEach(train -> genDailyTrain(date, train));
        LOG.info("生成{}的每日车次信息，任务结束", date);
        return CommonResp.success(null);
    }


    @Transactional
    public void genDailyTrain(Date date, Train train) {
        //清空每日车次数据
        DailyTrainExample dailyTrainExample = new DailyTrainExample();
        dailyTrainExample.createCriteria()
                .andDateEqualTo(date)
                .andCodeEqualTo(train.getCode());
        dailyTrainMapper.deleteByExample(dailyTrainExample);
        //生成每日车次数据
        DailyTrain dailyTrain = BeanUtil.copyProperties(train, DailyTrain.class);
        dailyTrain.setDate(date);
        dailyTrain.setCreateTime(DateTime.now());
        dailyTrain.setUpdateTime(DateTime.now());
        dailyTrain.setId(IdUtil.getSnowflake(1, 1).nextId());
        dailyTrainMapper.insertSelective(dailyTrain);

        //生成每日车次车站数据
        dailyTrainStationService.genDailyTrainStation(date, train.getCode());
        //生成每日车次车厢数据
        dailyTrainCarriageService.genDailyTrainCarriage(date, train.getCode());
        //生成每日座位数据
        dailyTrainSeatService.genDailyTrainSeat(date, train.getCode());
        //生成每日余票数据
        dailyTrainTicketService.genDailyTrainTicket(date, train.getCode());
    }
}