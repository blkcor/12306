package com.github.blkcor.service;


import com.github.blkcor.req.ConfirmOrderDoReq;

public interface BeforeConfirmOrderService {
    /**
     * 执行确认订单前的操作
     */
    String beforeDoConfirmOrder(ConfirmOrderDoReq confirmOrderSaveReq);
}
