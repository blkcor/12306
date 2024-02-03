package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.*;
import com.github.blkcor.mapper.DailyTrainSeatMapper;
import com.github.blkcor.mapper.TrainMapper;
import com.github.blkcor.req.DailyTrainSeatQueryReq;
import com.github.blkcor.req.DailyTrainSeatSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainSeatQueryResp;
import com.github.blkcor.resp.TrainQueryResp;
import com.github.blkcor.service.DailyTrainSeatService;
import com.github.blkcor.service.TrainService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DailyTrainSeatServiceImpl implements DailyTrainSeatService {
    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainSeatServiceImpl.class);
    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;
    @Resource
    private TrainMapper trainMapper;
    @Override
    public CommonResp<Void> saveDailyTrainSeat(DailyTrainSeatSaveReq dailyTrainSeatSaveReq) {
        DailyTrainSeat dailyTrainSeat  = BeanUtil.copyProperties(dailyTrainSeatSaveReq, DailyTrainSeat.class);
        if(ObjectUtil.isNull(dailyTrainSeat.getId())){
            dailyTrainSeat.setCreateTime(DateTime.now());
            dailyTrainSeat.setUpdateTime(DateTime.now());
            dailyTrainSeat.setId(IdUtil.getSnowflake(1,1).nextId());
            dailyTrainSeatMapper.insertSelective(dailyTrainSeat);
        }else{
            dailyTrainSeat.setUpdateTime(DateTime.now());
            dailyTrainSeatMapper.updateByPrimaryKeySelective(dailyTrainSeat);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<DailyTrainSeatQueryResp>> queryDailyTrainSeatList(DailyTrainSeatQueryReq dailyTrainSeatQueryReq) {
        DailyTrainSeatExample dailyTrainSeatExample = new DailyTrainSeatExample();
        dailyTrainSeatExample.setOrderByClause("train_code asc,carriage_index asc,carriage_seat_index asc");
        DailyTrainSeatExample.Criteria criteria = dailyTrainSeatExample.createCriteria();
        if(ObjectUtil.isNotEmpty(dailyTrainSeatQueryReq.getTrainCode())){
            criteria.andTrainCodeEqualTo(dailyTrainSeatQueryReq.getTrainCode());
        }
        LOG.info("查询页码：{}",dailyTrainSeatQueryReq.getPage());
        LOG.info("查询条数：{}",dailyTrainSeatQueryReq.getSize());

        PageHelper.startPage(dailyTrainSeatQueryReq.getPage(),dailyTrainSeatQueryReq.getSize());
        List<DailyTrainSeat> dailyTrainSeats = dailyTrainSeatMapper.selectByExample(dailyTrainSeatExample);
        PageInfo<DailyTrainSeat> pageInfo = new PageInfo<>(dailyTrainSeats);

        LOG.info("总条数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        List<DailyTrainSeatQueryResp> list = BeanUtil.copyToList(dailyTrainSeats, DailyTrainSeatQueryResp.class);
        PageResp<DailyTrainSeatQueryResp> dailyTrainSeatQueryRespPageResp = new PageResp<>();
        dailyTrainSeatQueryRespPageResp.setList(list);
        dailyTrainSeatQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(dailyTrainSeatQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteDailyTrainSeat(Long id) {
        dailyTrainSeatMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }
}