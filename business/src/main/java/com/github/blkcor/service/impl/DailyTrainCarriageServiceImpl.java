package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.DailyTrainCarriage;
import com.github.blkcor.entity.DailyTrainCarriageExample;
import com.github.blkcor.entity.TrainCarriage;
import com.github.blkcor.entity.TrainCarriageExample;
import com.github.blkcor.mapper.DailyTrainCarriageMapper;
import com.github.blkcor.mapper.TrainCarriageMapper;
import com.github.blkcor.req.DailyTrainCarriageQueryReq;
import com.github.blkcor.req.DailyTrainCarriageSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainCarriageQueryResp;
import com.github.blkcor.service.DailyTrainCarriageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class DailyTrainCarriageServiceImpl implements DailyTrainCarriageService {
    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainCarriageServiceImpl.class);
    @Resource
    private DailyTrainCarriageMapper dailyTrainCarriageMapper;
    @Resource
    private TrainCarriageMapper trainCarriageMapper;

    @Override
    public CommonResp<Void> saveDailyTrainCarriage(DailyTrainCarriageSaveReq dailyTrainCarriageSaveReq) {
        DailyTrainCarriage dailyTrainCarriage = BeanUtil.copyProperties(dailyTrainCarriageSaveReq, DailyTrainCarriage.class);
        if (ObjectUtil.isNull(dailyTrainCarriage.getId())) {
            dailyTrainCarriage.setCreateTime(DateTime.now());
            dailyTrainCarriage.setUpdateTime(DateTime.now());
            dailyTrainCarriage.setId(IdUtil.getSnowflake(1, 1).nextId());
            dailyTrainCarriageMapper.insertSelective(dailyTrainCarriage);
        } else {
            dailyTrainCarriage.setUpdateTime(DateTime.now());
            dailyTrainCarriageMapper.updateByPrimaryKeySelective(dailyTrainCarriage);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<DailyTrainCarriageQueryResp>> queryDailyTrainCarriageList(DailyTrainCarriageQueryReq dailyTrainCarriageQueryReq) {
        DailyTrainCarriageExample dailyTrainCarriageExample = new DailyTrainCarriageExample();
        dailyTrainCarriageExample.setOrderByClause("`date` desc,`train_code` asc,`index` asc");
        DailyTrainCarriageExample.Criteria criteria = dailyTrainCarriageExample.createCriteria();
        if (ObjectUtil.isNotEmpty(dailyTrainCarriageQueryReq.getTrainCode())) {
            criteria.andTrainCodeEqualTo(dailyTrainCarriageQueryReq.getTrainCode());
        }

        if (ObjectUtil.isNotEmpty(dailyTrainCarriageQueryReq.getDate())) {
            criteria.andDateEqualTo(dailyTrainCarriageQueryReq.getDate());
        }
        LOG.info("查询页码：{}", dailyTrainCarriageQueryReq.getPage());
        LOG.info("查询条数：{}", dailyTrainCarriageQueryReq.getSize());

        PageHelper.startPage(dailyTrainCarriageQueryReq.getPage(), dailyTrainCarriageQueryReq.getSize());
        List<DailyTrainCarriage> dailyTrainCarriages = dailyTrainCarriageMapper.selectByExample(dailyTrainCarriageExample);
        PageInfo<DailyTrainCarriage> pageInfo = new PageInfo<>(dailyTrainCarriages);

        LOG.info("总条数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainCarriageQueryResp> list = BeanUtil.copyToList(dailyTrainCarriages, DailyTrainCarriageQueryResp.class);
        PageResp<DailyTrainCarriageQueryResp> dailyTrainCarriageQueryRespPageResp = new PageResp<>();
        dailyTrainCarriageQueryRespPageResp.setList(list);
        dailyTrainCarriageQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(dailyTrainCarriageQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteDailyTrainCarriage(Long id) {
        dailyTrainCarriageMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<Void> genDailyTrainCarriage(Date date, String code) {
        LOG.info("生成每日车次车厢数据，日期：{}，车次：{}，任务开始！", date, code);
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        trainCarriageExample.createCriteria().andTrainCodeEqualTo(code);
        List<TrainCarriage> trainCarriageList = trainCarriageMapper.selectByExample(trainCarriageExample);
        if (CollUtil.isEmpty(trainCarriageList)) {
            LOG.info("车次{}没有找到对应的车厢信息", code);
            return CommonResp.fail("车次没有找到对应的车厢信息");
        }
        DailyTrainCarriageExample dailyTrainCarriageExample = new DailyTrainCarriageExample();
        dailyTrainCarriageExample.createCriteria().andDateEqualTo(date).andTrainCodeEqualTo(code);
        dailyTrainCarriageMapper.deleteByExample(dailyTrainCarriageExample);

        trainCarriageList.forEach(trainCarriage -> genDailyCarriage(date, trainCarriage));
        LOG.info("生成每日车次车厢数据，日期：{}，车次：{}，任务结束！", date, code);
        return CommonResp.success(null);
    }

    @Override
    public List<DailyTrainCarriage> selectBySeatType(String trainCode, Date date, String seatType) {
        DailyTrainCarriageExample dailyTrainCarriageExample = new DailyTrainCarriageExample();
        dailyTrainCarriageExample.createCriteria().andDateEqualTo(date).andTrainCodeEqualTo(trainCode).andSeatTypeEqualTo(seatType);
        return dailyTrainCarriageMapper.selectByExample(dailyTrainCarriageExample);
    }

    private void genDailyCarriage(Date date, TrainCarriage trainCarriage) {
        DailyTrainCarriage dailyTrainCarriage = BeanUtil.copyProperties(trainCarriage, DailyTrainCarriage.class);
        dailyTrainCarriage.setDate(date);
        dailyTrainCarriage.setId(IdUtil.getSnowflake(1, 1).nextId());
        dailyTrainCarriage.setCreateTime(DateTime.now());
        dailyTrainCarriage.setUpdateTime(DateTime.now());
        dailyTrainCarriageMapper.insertSelective(dailyTrainCarriage);
    }
}