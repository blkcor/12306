package com.github.blkcor.controller.web;

import com.github.blkcor.req.ConfirmOrderQueryReq;
import com.github.blkcor.req.ConfirmOrderDoReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.ConfirmOrderQueryResp;
import com.github.blkcor.service.BeforeConfirmOrderService;
import com.github.blkcor.service.ConfirmOrderService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/confirmOrder")
public class ConfirmOrderController {
    @Resource
    private BeforeConfirmOrderService beforeConfirmOrderService;

    @PostMapping("/do")
    public CommonResp<Void> doConfirmOrder(@RequestBody  @Valid ConfirmOrderDoReq confirmOrderSaveReq) {
        beforeConfirmOrderService.beforeDoConfirmOrder(confirmOrderSaveReq);
        return CommonResp.success(null);
    }
}
