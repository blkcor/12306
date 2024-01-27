package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.TrainSeat;
import com.github.blkcor.entity.TrainSeatExample;
import com.github.blkcor.mapper.TrainSeatMapper;
import com.github.blkcor.req.TrainSeatQueryReq;
import com.github.blkcor.req.TrainSeatSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.TrainSeatQueryResp;
import com.github.blkcor.service.TrainSeatService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TrainSeatServiceImpl implements TrainSeatService {
    private static final Logger LOG = LoggerFactory.getLogger(TrainSeatServiceImpl.class);
    @Resource
    private TrainSeatMapper trainSeatMapper;

    @Override
    public CommonResp<Void> saveTrainSeat(TrainSeatSaveReq trainSeatSaveReq) {
        TrainSeat trainSeat  = BeanUtil.copyProperties(trainSeatSaveReq, TrainSeat.class);
        if(ObjectUtil.isNull(trainSeat.getId())){
            trainSeat.setCreateTime(DateTime.now());
            trainSeat.setUpdateTime(DateTime.now());
            trainSeat.setId(IdUtil.getSnowflake(1,1).nextId());
            trainSeatMapper.insertSelective(trainSeat);
        }else{
            trainSeat.setUpdateTime(DateTime.now());
            trainSeatMapper.updateByPrimaryKeySelective(trainSeat);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<TrainSeatQueryResp>> queryTrainSeatList(TrainSeatQueryReq trainSeatQueryReq) {
        TrainSeatExample trainSeatExample = new TrainSeatExample();
        TrainSeatExample.Criteria criteria = trainSeatExample.createCriteria();

        LOG.info("查询页码：{}",trainSeatQueryReq.getPage());
        LOG.info("查询条数：{}",trainSeatQueryReq.getSize());

        PageHelper.startPage(trainSeatQueryReq.getPage(),trainSeatQueryReq.getSize());
        List<TrainSeat> trainSeats = trainSeatMapper.selectByExample(trainSeatExample);
        PageInfo<TrainSeat> pageInfo = new PageInfo<>(trainSeats);

        LOG.info("总条数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        List<TrainSeatQueryResp> list = BeanUtil.copyToList(trainSeats, TrainSeatQueryResp.class);
        PageResp<TrainSeatQueryResp> trainSeatQueryRespPageResp = new PageResp<>();
        trainSeatQueryRespPageResp.setList(list);
        trainSeatQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(trainSeatQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteTrainSeat(Long id) {
        trainSeatMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }
}