package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.blkcor.context.LoginMemberContext;
import com.github.blkcor.entity.Passenger;
import com.github.blkcor.mapper.PassengerMapper;
import com.github.blkcor.req.PassengerSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.service.PassengerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


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
}
