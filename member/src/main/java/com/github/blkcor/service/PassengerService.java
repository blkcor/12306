package com.github.blkcor.service;

import com.github.blkcor.req.PassengerSaveReq;
import com.github.blkcor.resp.CommonResp;

public interface PassengerService {
    /**
     * 保存乘客信息
     * @param passengerSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> savePassenger(PassengerSaveReq passengerSaveReq);
}
