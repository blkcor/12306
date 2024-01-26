package com.github.blkcor.service;

import com.github.blkcor.req.PassengerQueryReq;
import com.github.blkcor.req.PassengerSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.PassengerQueryResp;


public interface PassengerService {
    /**
     * 保存乘客信息
     * @param passengerSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> savePassenger(PassengerSaveReq passengerSaveReq);

    /**
     * 查询乘客列表
     * @param passengerQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<PassengerQueryResp>> queryPassengerList(PassengerQueryReq passengerQueryReq);


    /**
     * 删除乘客信息
     * @param id 乘客id
     * @return 返回结果
     */
    CommonResp<Void> deletePassenger(Long id);
}