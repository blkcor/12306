package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.TrainCarriage;
import com.github.blkcor.entity.TrainCarriageExample;
import com.github.blkcor.enums.SeatColEnum;
import com.github.blkcor.exception.BusinessException;
import com.github.blkcor.exception.BusinessExceptionEnum;
import com.github.blkcor.mapper.TrainCarriageMapper;
import com.github.blkcor.req.TrainCarriageQueryReq;
import com.github.blkcor.req.TrainCarriageSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.TrainCarriageQueryResp;
import com.github.blkcor.service.TrainCarriageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TrainCarriageServiceImpl implements TrainCarriageService {
    private static final Logger LOG = LoggerFactory.getLogger(TrainCarriageServiceImpl.class);
    @Resource
    private TrainCarriageMapper trainCarriageMapper;

    @Override
    public CommonResp<Void> saveTrainCarriage(TrainCarriageSaveReq trainCarriageSaveReq) {
        TrainCarriage trainCarriage = BeanUtil.copyProperties(trainCarriageSaveReq, TrainCarriage.class);
        List<SeatColEnum> colList = SeatColEnum.getColsByType(trainCarriage.getSeatType());
        trainCarriage.setColCount(colList.size());
        trainCarriage.setSeatCount(trainCarriage.getRowCount() * trainCarriage.getColCount());
        if (ObjectUtil.isNull(trainCarriage.getId())) {
            //校验车厢是否存在
            TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
            trainCarriageExample.createCriteria().andTrainCodeEqualTo(trainCarriageSaveReq.getTrainCode()).andIndexEqualTo(trainCarriageSaveReq.getIndex());
            long count = trainCarriageMapper.countByExample(trainCarriageExample);
            if (count != 0) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_CARRIAGE_CODE_INDEX_UNIQUE_ERROR);
            }
            trainCarriage.setCreateTime(DateTime.now());
            trainCarriage.setUpdateTime(DateTime.now());
            trainCarriage.setId(IdUtil.getSnowflake(1, 1).nextId());
            trainCarriageMapper.insertSelective(trainCarriage);
        } else {
            trainCarriage.setUpdateTime(DateTime.now());
            trainCarriageMapper.updateByPrimaryKeySelective(trainCarriage);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<TrainCarriageQueryResp>> queryTrainCarriageList(TrainCarriageQueryReq trainCarriageQueryReq) {
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        TrainCarriageExample.Criteria criteria = trainCarriageExample.createCriteria();

        LOG.info("查询页码：{}", trainCarriageQueryReq.getPage());
        LOG.info("查询条数：{}", trainCarriageQueryReq.getSize());

        PageHelper.startPage(trainCarriageQueryReq.getPage(), trainCarriageQueryReq.getSize());
        List<TrainCarriage> trainCarriages = trainCarriageMapper.selectByExample(trainCarriageExample);
        PageInfo<TrainCarriage> pageInfo = new PageInfo<>(trainCarriages);

        LOG.info("总条数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainCarriageQueryResp> list = BeanUtil.copyToList(trainCarriages, TrainCarriageQueryResp.class);
        PageResp<TrainCarriageQueryResp> trainCarriageQueryRespPageResp = new PageResp<>();
        trainCarriageQueryRespPageResp.setList(list);
        trainCarriageQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(trainCarriageQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteTrainCarriage(Long id) {
        trainCarriageMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }
}