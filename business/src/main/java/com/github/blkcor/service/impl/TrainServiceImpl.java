package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.Train;
import com.github.blkcor.entity.TrainExample;
import com.github.blkcor.exception.BusinessException;
import com.github.blkcor.exception.BusinessExceptionEnum;
import com.github.blkcor.mapper.TrainMapper;
import com.github.blkcor.req.TrainQueryReq;
import com.github.blkcor.req.TrainSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.TrainQueryResp;
import com.github.blkcor.service.TrainService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TrainServiceImpl implements TrainService {
    private static final Logger LOG = LoggerFactory.getLogger(TrainServiceImpl.class);
    @Resource
    private TrainMapper trainMapper;

    @Override
    public CommonResp<Void> saveTrain(TrainSaveReq trainSaveReq) {
        Train train  = BeanUtil.copyProperties(trainSaveReq, Train.class);
        if(ObjectUtil.isNull(train.getId())){
            //校验车次是否存在
            TrainExample trainExample = new TrainExample();
            trainExample.createCriteria().andCodeEqualTo(train.getCode());
            long count = trainMapper.countByExample(trainExample);
            if(count != 0){
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_CODE_UNIQUE_ERROR);
            }
            train.setCreateTime(DateTime.now());
            train.setUpdateTime(DateTime.now());
            train.setId(IdUtil.getSnowflake(1,1).nextId());
            trainMapper.insertSelective(train);
        }else{
            train.setUpdateTime(DateTime.now());
            trainMapper.updateByPrimaryKeySelective(train);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<TrainQueryResp>> queryTrainList(TrainQueryReq trainQueryReq) {
        TrainExample trainExample = new TrainExample();
        TrainExample.Criteria criteria = trainExample.createCriteria();

        LOG.info("查询页码：{}",trainQueryReq.getPage());
        LOG.info("查询条数：{}",trainQueryReq.getSize());

        PageHelper.startPage(trainQueryReq.getPage(),trainQueryReq.getSize());
        List<Train> trains = trainMapper.selectByExample(trainExample);
        PageInfo<Train> pageInfo = new PageInfo<>(trains);

        LOG.info("总条数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        List<TrainQueryResp> list = BeanUtil.copyToList(trains, TrainQueryResp.class);
        PageResp<TrainQueryResp> trainQueryRespPageResp = new PageResp<>();
        trainQueryRespPageResp.setList(list);
        trainQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(trainQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteTrain(Long id) {
        trainMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<List<TrainQueryResp>> queryAllTrainList() {
        TrainExample trainExample = new TrainExample();
        trainExample.setOrderByClause("code asc");
        List<Train> trains = trainMapper.selectByExample(trainExample);
        List<TrainQueryResp> list = BeanUtil.copyToList(trains, TrainQueryResp.class);
        return CommonResp.success(list);
    }
}