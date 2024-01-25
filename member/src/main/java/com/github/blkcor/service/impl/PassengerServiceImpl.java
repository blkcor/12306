package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.context.LoginMemberContext;
import com.github.blkcor.entity.Passenger;
import com.github.blkcor.entity.PassengerExample;
import com.github.blkcor.mapper.PassengerMapper;
import com.github.blkcor.req.PassengerQueryReq;
import com.github.blkcor.req.PassengerSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PassengerQueryResp;
import com.github.blkcor.service.PassengerService;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PassengerServiceImpl implements PassengerService {

    @Resource
    private PassengerMapper passengerMapper;

    @Override
    public CommonResp<Void> savePassenger(PassengerSaveReq passengerSaveReq) {
        Passenger passenger  = BeanUtil.copyProperties(passengerSaveReq, Passenger.class);
        passenger.setMemberId(LoginMemberContext.getId());
        passenger.setCreateTime(DateTime.now());
        passenger.setUpdateTime(DateTime.now());
        passenger.setId(IdUtil.getSnowflake(1,1).nextId());
        passengerMapper.insertSelective(passenger);
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<List<PassengerQueryResp>> queryPassengerList( PassengerQueryReq passengerQueryReq) {
        PassengerExample passengerExample = new PassengerExample();
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        if(ObjectUtil.isNotNull(passengerQueryReq.getMemberId())){
            criteria.andMemberIdEqualTo(passengerQueryReq.getMemberId());
        }
        PageHelper.startPage(passengerQueryReq.getPage(),passengerQueryReq.getSize());
        List<Passenger> passengers = passengerMapper.selectByExample(passengerExample);
        return CommonResp.success(BeanUtil.copyToList(passengers, PassengerQueryResp.class));
    }
}
