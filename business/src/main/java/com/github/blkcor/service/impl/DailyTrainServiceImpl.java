package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.DailyTrain;
import com.github.blkcor.entity.DailyTrainExample;
import com.github.blkcor.mapper.DailyTrainMapper;
import com.github.blkcor.req.DailyTrainQueryReq;
import com.github.blkcor.req.DailyTrainSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainQueryResp;
import com.github.blkcor.service.DailyTrainService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DailyTrainServiceImpl implements DailyTrainService {
    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainServiceImpl.class);
    @Resource
    private DailyTrainMapper dailyTrainMapper;

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
}