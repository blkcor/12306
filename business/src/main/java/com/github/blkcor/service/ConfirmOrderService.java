package com.github.blkcor.service;

import com.github.blkcor.dto.ConfirmOrderDto;
import com.github.blkcor.entity.ConfirmOrder;
import com.github.blkcor.req.ConfirmOrderQueryReq;
import com.github.blkcor.req.ConfirmOrderDoReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.ConfirmOrderQueryResp;


public interface ConfirmOrderService {

    /**
     * 保存确认订单信息
     * @param confirmOrderSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveConfirmOrder(ConfirmOrderDoReq confirmOrderSaveReq);

    /**
     * 查询确认订单列表
     * @param confirmOrderQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<ConfirmOrderQueryResp>> queryConfirmOrderList(ConfirmOrderQueryReq confirmOrderQueryReq);


    /**
     * 删除确认订单信息
     * @param id 确认订单id
     * @return 返回结果
     */
    CommonResp<Void> deleteConfirmOrder(Long id);

    /**
     * 执行确认订单
     *
     * @param confirmOrderDto 请求参数
     */
    void doConfirmOrder(ConfirmOrderDto confirmOrderDto);

    /**
     * 更新订单状态
     * @param confirmOrder 请求参数
     */
    void updateStatus(ConfirmOrder confirmOrder);

    /**
     * 查询排队人数
     * @param id 订单id
     * @return 返回结果
     */
    CommonResp<Integer> queryLineCount(Long id);
}
