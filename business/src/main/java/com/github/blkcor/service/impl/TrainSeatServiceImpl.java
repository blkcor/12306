package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.blkcor.entity.TrainCarriage;
import com.github.blkcor.entity.TrainCarriageExample;
import com.github.blkcor.entity.TrainSeat;
import com.github.blkcor.entity.TrainSeatExample;
import com.github.blkcor.enums.SeatColEnum;
import com.github.blkcor.mapper.TrainCarriageMapper;
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
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class TrainSeatServiceImpl implements TrainSeatService {
    private static final Logger LOG = LoggerFactory.getLogger(TrainSeatServiceImpl.class);
    @Resource
    private TrainSeatMapper trainSeatMapper;

    @Resource
    private TrainCarriageMapper trainCarriageMapper;

    @Override
    public CommonResp<Void> saveTrainSeat(TrainSeatSaveReq trainSeatSaveReq) {
        TrainSeat trainSeat = BeanUtil.copyProperties(trainSeatSaveReq, TrainSeat.class);
        if (ObjectUtil.isNull(trainSeat.getId())) {
            trainSeat.setCreateTime(DateTime.now());
            trainSeat.setUpdateTime(DateTime.now());
            trainSeat.setId(IdUtil.getSnowflake(1, 1).nextId());
            trainSeatMapper.insertSelective(trainSeat);
        } else {
            trainSeat.setUpdateTime(DateTime.now());
            trainSeatMapper.updateByPrimaryKeySelective(trainSeat);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<TrainSeatQueryResp>> queryTrainSeatList(TrainSeatQueryReq trainSeatQueryReq) {
        TrainSeatExample trainSeatExample = new TrainSeatExample();
        trainSeatExample.setOrderByClause("train_code asc,carriage_index asc,carriage_seat_index asc");
        TrainSeatExample.Criteria criteria = trainSeatExample.createCriteria();
        if (StrUtil.isNotBlank(trainSeatQueryReq.getTrainCode())) {
            criteria.andTrainCodeEqualTo(trainSeatQueryReq.getTrainCode());
        }
        LOG.info("查询页码：{}", trainSeatQueryReq.getPage());
        LOG.info("查询条数：{}", trainSeatQueryReq.getSize());

        PageHelper.startPage(trainSeatQueryReq.getPage(), trainSeatQueryReq.getSize());
        List<TrainSeat> trainSeats = trainSeatMapper.selectByExample(trainSeatExample);
        PageInfo<TrainSeat> pageInfo = new PageInfo<>(trainSeats);

        LOG.info("总条数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

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

    @Override
    public CommonResp<Void> genSeat(String trainCode) {
        //1、删除掉当前车次所有的座位信息
        TrainSeatExample trainSeatExample = new TrainSeatExample();
        trainSeatExample.createCriteria().andTrainCodeEqualTo(trainCode);
        trainSeatMapper.deleteByExample(trainSeatExample);
        //2、根据trainCode查询车厢信息
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        trainCarriageExample.createCriteria().andTrainCodeEqualTo(trainCode);
        List<TrainCarriage> trainCarriageList = trainCarriageMapper.selectByExample(trainCarriageExample);
        //3、根据车厢信息生成座位信息
        trainCarriageList.forEach(trainCarriage -> {
            Integer rowCount = trainCarriage.getRowCount();
            String seatType = trainCarriage.getSeatType();
            List<SeatColEnum> colList = SeatColEnum.getColsByType(seatType);
            for (int row = 1; row <= rowCount; row++) {
                AtomicInteger carriageSeatIndex = new AtomicInteger(1);
                int finalRow = row;
                colList.forEach(col -> {
                    //构造座位数据并保存
                    TrainSeat trainSeat = new TrainSeat();
                    trainSeat.setId(IdUtil.getSnowflake(1, 1).nextId());
                    trainSeat.setTrainCode(trainCode);
                    trainSeat.setCarriageIndex(trainCarriage.getIndex());
                    trainSeat.setRow(StrUtil.fillBefore(String.valueOf(finalRow), '0', 2));
                    trainSeat.setCol(col.getCode());
                    trainSeat.setSeatType(seatType);
                    trainSeat.setCarriageSeatIndex(carriageSeatIndex.getAndIncrement());
                    trainSeat.setUpdateTime(DateTime.now());
                    trainSeat.setCreateTime(DateTime.now());
                    trainSeatMapper.insert(trainSeat);
                });
            }
        });
        return CommonResp.success(null);
    }
}