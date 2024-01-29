package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.DailyTrainStation;
import com.github.blkcor.entity.DailyTrainStationExample;
import com.github.blkcor.mapper.DailyTrainStationMapper;
import com.github.blkcor.req.DailyTrainStationQueryReq;
import com.github.blkcor.req.DailyTrainStationSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainStationQueryResp;
import com.github.blkcor.service.DailyTrainStationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DailyTrainStationServiceImpl implements DailyTrainStationService {
    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainStationServiceImpl.class);
    @Resource
    private DailyTrainStationMapper dailyTrainStationMapper;

    @Override
    public CommonResp<Void> saveDailyTrainStation(DailyTrainStationSaveReq dailyTrainStationSaveReq) {
        DailyTrainStation dailyTrainStation = BeanUtil.copyProperties(dailyTrainStationSaveReq, DailyTrainStation.class);
        if (ObjectUtil.isNull(dailyTrainStation.getId())) {
            dailyTrainStation.setCreateTime(DateTime.now());
            dailyTrainStation.setUpdateTime(DateTime.now());
            dailyTrainStation.setId(IdUtil.getSnowflake(1, 1).nextId());
            dailyTrainStationMapper.insertSelective(dailyTrainStation);
        } else {
            dailyTrainStation.setUpdateTime(DateTime.now());
            dailyTrainStationMapper.updateByPrimaryKeySelective(dailyTrainStation);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<DailyTrainStationQueryResp>> queryDailyTrainStationList(DailyTrainStationQueryReq dailyTrainStationQueryReq) {
        DailyTrainStationExample dailyTrainStationExample = new DailyTrainStationExample();
        dailyTrainStationExample.setOrderByClause("`date` desc,`train_code` asc,`index` asc");
        DailyTrainStationExample.Criteria criteria = dailyTrainStationExample.createCriteria();
        if (ObjectUtil.isNotEmpty(dailyTrainStationQueryReq.getCode())) {
            criteria.andTrainCodeEqualTo(dailyTrainStationQueryReq.getCode());
        }
        if (ObjectUtil.isNotEmpty(dailyTrainStationQueryReq.getDate())) {
            criteria.andDateEqualTo(dailyTrainStationQueryReq.getDate());
        }
        LOG.info("查询页码：{}", dailyTrainStationQueryReq.getPage());
        LOG.info("查询条数：{}", dailyTrainStationQueryReq.getSize());

        PageHelper.startPage(dailyTrainStationQueryReq.getPage(), dailyTrainStationQueryReq.getSize());
        List<DailyTrainStation> dailyTrainStations = dailyTrainStationMapper.selectByExample(dailyTrainStationExample);
        PageInfo<DailyTrainStation> pageInfo = new PageInfo<>(dailyTrainStations);

        LOG.info("总条数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainStationQueryResp> list = BeanUtil.copyToList(dailyTrainStations, DailyTrainStationQueryResp.class);
        PageResp<DailyTrainStationQueryResp> dailyTrainStationQueryRespPageResp = new PageResp<>();
        dailyTrainStationQueryRespPageResp.setList(list);
        dailyTrainStationQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(dailyTrainStationQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteDailyTrainStation(Long id) {
        dailyTrainStationMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }
}