package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.TrainStation;
import com.github.blkcor.entity.TrainStationExample;
import com.github.blkcor.mapper.TrainStationMapper;
import com.github.blkcor.req.TrainStationQueryReq;
import com.github.blkcor.req.TrainStationSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.TrainStationQueryResp;
import com.github.blkcor.service.TrainStationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TrainStationServiceImpl implements TrainStationService {
    private static final Logger LOG = LoggerFactory.getLogger(TrainStationServiceImpl.class);
    @Resource
    private TrainStationMapper trainStationMapper;

    @Override
    public CommonResp<Void> saveTrainStation(TrainStationSaveReq trainStationSaveReq) {
        TrainStation trainStation  = BeanUtil.copyProperties(trainStationSaveReq, TrainStation.class);
        if(ObjectUtil.isNull(trainStation.getId())){
            trainStation.setCreateTime(DateTime.now());
            trainStation.setUpdateTime(DateTime.now());
            trainStation.setId(IdUtil.getSnowflake(1,1).nextId());
            trainStationMapper.insertSelective(trainStation);
        }else{
            trainStation.setUpdateTime(DateTime.now());
            trainStationMapper.updateByPrimaryKeySelective(trainStation);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<TrainStationQueryResp>> queryTrainStationList(TrainStationQueryReq trainStationQueryReq) {
        TrainStationExample trainStationExample = new TrainStationExample();
        TrainStationExample.Criteria criteria = trainStationExample.createCriteria();

        LOG.info("查询页码：{}",trainStationQueryReq.getPage());
        LOG.info("查询条数：{}",trainStationQueryReq.getSize());

        PageHelper.startPage(trainStationQueryReq.getPage(),trainStationQueryReq.getSize());
        List<TrainStation> trainStations = trainStationMapper.selectByExample(trainStationExample);
        PageInfo<TrainStation> pageInfo = new PageInfo<>(trainStations);

        LOG.info("总条数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        List<TrainStationQueryResp> list = BeanUtil.copyToList(trainStations, TrainStationQueryResp.class);
        PageResp<TrainStationQueryResp> trainStationQueryRespPageResp = new PageResp<>();
        trainStationQueryRespPageResp.setList(list);
        trainStationQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(trainStationQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteTrainStation(Long id) {
        trainStationMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }
}