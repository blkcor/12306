package com.github.blkcor.controller.admin;

import com.github.blkcor.req.ConfirmOrderDoReq;
import com.github.blkcor.req.ConfirmOrderQueryReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.ConfirmOrderQueryResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.service.ConfirmOrderService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/confirmOrder")
public class AdminConfirmOrderController {
    @Resource
    private ConfirmOrderService confirmOrderService;

    @PostMapping("/save")
    public CommonResp<Void> saveConfirmOrder(@RequestBody  @Valid ConfirmOrderDoReq confirmOrderSaveReq) {
        return confirmOrderService.saveConfirmOrder(confirmOrderSaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<ConfirmOrderQueryResp>> queryConfirmOrderList(@Valid ConfirmOrderQueryReq confirmOrderQueryReq) {
        return confirmOrderService.queryConfirmOrderList(confirmOrderQueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> updateConfirmOrder(@RequestBody @Valid ConfirmOrderDoReq confirmOrderSaveReq) {
        return confirmOrderService.saveConfirmOrder(confirmOrderSaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deleteConfirmOrder(@PathVariable Long id) {
        return confirmOrderService.deleteConfirmOrder(id);
    }
}
