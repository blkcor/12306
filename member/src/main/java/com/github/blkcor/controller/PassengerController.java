package com.github.blkcor.controller;

import com.github.blkcor.context.LoginMemberContext;
import com.github.blkcor.req.PassengerQueryReq;
import com.github.blkcor.req.PassengerSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.PassengerQueryResp;
import com.github.blkcor.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/passenger")
public class PassengerController {
    @Resource
    private PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp<Void> savePassenger(@RequestBody  @Valid PassengerSaveReq passengerSaveReq) {
        return passengerService.savePassenger(passengerSaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<PassengerQueryResp>> queryPassengerList(@Valid PassengerQueryReq passengerQueryReq) {
        passengerQueryReq.setMemberId(LoginMemberContext.getId());
        return passengerService.queryPassengerList(passengerQueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> updatePassenger(@RequestBody @Valid PassengerSaveReq passengerSaveReq) {
        return passengerService.savePassenger(passengerSaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deletePassenger(@PathVariable Long id) {
        return passengerService.deletePassenger(id);
    }
}
