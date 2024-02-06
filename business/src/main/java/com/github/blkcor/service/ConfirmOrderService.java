package com.github.blkcor.service;

import com.github.blkcor.req.ConfirmOrderQueryReq;
import com.github.blkcor.req.ConfirmOrderSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.ConfirmOrderQueryResp;


public interface ConfirmOrderService {

    /**
     * 保存确认订单信息
     * @param confirmOrderSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveConfirmOrder(ConfirmOrderSaveReq confirmOrderSaveReq);

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
}