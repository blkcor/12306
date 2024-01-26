package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.Passenger;
import com.github.blkcor.entity.PassengerExample;
import com.github.blkcor.mapper.PassengerMapper;
import com.github.blkcor.req.PassengerQueryReq;
import com.github.blkcor.req.PassengerSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.PassengerQueryResp;
import com.github.blkcor.service.PassengerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PassengerServiceImpl implements PassengerService {
    private static final Logger LOG = LoggerFactory.getLogger(PassengerServiceImpl.class);
    @Resource
    private PassengerMapper passengerMapper;

    @Override
    public CommonResp<Void> savePassenger(PassengerSaveReq passengerSaveReq) {
        Passenger passenger  = BeanUtil.copyProperties(passengerSaveReq, Passenger.class);
        if(ObjectUtil.isNull(passenger.getId())){
            passenger.setCreateTime(DateTime.now());
            passenger.setUpdateTime(DateTime.now());
            passenger.setId(IdUtil.getSnowflake(1,1).nextId());
            passengerMapper.insertSelective(passenger);
        }else{
            passenger.setUpdateTime(DateTime.now());
            passengerMapper.updateByPrimaryKeySelective(passenger);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<PassengerQueryResp>> queryPassengerList(PassengerQueryReq passengerQueryReq) {
        PassengerExample passengerExample = new PassengerExample();
        PassengerExample.Criteria criteria = passengerExample.createCriteria();

        LOG.info("查询页码：{}",passengerQueryReq.getPage());
        LOG.info("查询条数：{}",passengerQueryReq.getSize());

        PageHelper.startPage(passengerQueryReq.getPage(),passengerQueryReq.getSize());
        List<Passenger> passengers = passengerMapper.selectByExample(passengerExample);
        PageInfo<Passenger> pageInfo = new PageInfo<>(passengers);

        LOG.info("总条数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        List<PassengerQueryResp> list = BeanUtil.copyToList(passengers, PassengerQueryResp.class);
        PageResp<PassengerQueryResp> passengerQueryRespPageResp = new PageResp<>();
        passengerQueryRespPageResp.setList(list);
        passengerQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(passengerQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deletePassenger(Long id) {
        passengerMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }
}