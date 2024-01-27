package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.Station;
import com.github.blkcor.entity.StationExample;
import com.github.blkcor.mapper.StationMapper;
import com.github.blkcor.req.StationQueryReq;
import com.github.blkcor.req.StationSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.StationQueryResp;
import com.github.blkcor.service.StationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StationServiceImpl implements StationService {
    private static final Logger LOG = LoggerFactory.getLogger(StationServiceImpl.class);
    @Resource
    private StationMapper stationMapper;

    @Override
    public CommonResp<Void> saveStation(StationSaveReq stationSaveReq) {
        Station station  = BeanUtil.copyProperties(stationSaveReq, Station.class);
        if(ObjectUtil.isNull(station.getId())){
            station.setCreateTime(DateTime.now());
            station.setUpdateTime(DateTime.now());
            station.setId(IdUtil.getSnowflake(1,1).nextId());
            stationMapper.insertSelective(station);
        }else{
            station.setUpdateTime(DateTime.now());
            stationMapper.updateByPrimaryKeySelective(station);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<StationQueryResp>> queryStationList(StationQueryReq stationQueryReq) {
        StationExample stationExample = new StationExample();
        StationExample.Criteria criteria = stationExample.createCriteria();

        LOG.info("查询页码：{}",stationQueryReq.getPage());
        LOG.info("查询条数：{}",stationQueryReq.getSize());

        PageHelper.startPage(stationQueryReq.getPage(),stationQueryReq.getSize());
        List<Station> stations = stationMapper.selectByExample(stationExample);
        PageInfo<Station> pageInfo = new PageInfo<>(stations);

        LOG.info("总条数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        List<StationQueryResp> list = BeanUtil.copyToList(stations, StationQueryResp.class);
        PageResp<StationQueryResp> stationQueryRespPageResp = new PageResp<>();
        stationQueryRespPageResp.setList(list);
        stationQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(stationQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteStation(Long id) {
        stationMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }
}