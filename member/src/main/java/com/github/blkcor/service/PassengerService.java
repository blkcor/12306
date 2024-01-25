package com.github.blkcor.service;

import com.github.blkcor.req.PassengerQueryReq;
import com.github.blkcor.req.PassengerSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PassengerQueryResp;

import java.util.List;

public interface PassengerService {
    /**
     * 保存乘客信息
     * @param passengerSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> savePassenger(PassengerSaveReq passengerSaveReq);

    /**
     * 查询乘客列表 根据memberId
     * @param passengerQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<List<PassengerQueryResp>> queryPassengerList(PassengerQueryReq passengerQueryReq);
}
