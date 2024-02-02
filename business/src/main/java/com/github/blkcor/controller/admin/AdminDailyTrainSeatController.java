package com.github.blkcor.controller.admin;

import com.github.blkcor.req.DailyTrainSeatQueryReq;
import com.github.blkcor.req.DailyTrainSeatSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainSeatQueryResp;
import com.github.blkcor.service.DailyTrainSeatService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/dailyTrainSeat")
public class AdminDailyTrainSeatController {
    @Resource
    private DailyTrainSeatService dailyTrainSeatService;

    @PostMapping("/save")
    public CommonResp<Void> saveDailyTrainSeat(@RequestBody  @Valid DailyTrainSeatSaveReq dailyTrainSeatSaveReq) {
        return dailyTrainSeatService.saveDailyTrainSeat(dailyTrainSeatSaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainSeatQueryResp>> queryDailyTrainSeatList(@Valid DailyTrainSeatQueryReq dailyTrainSeatQueryReq) {
        return dailyTrainSeatService.queryDailyTrainSeatList(dailyTrainSeatQueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> updateDailyTrainSeat(@RequestBody @Valid DailyTrainSeatSaveReq dailyTrainSeatSaveReq) {
        return dailyTrainSeatService.saveDailyTrainSeat(dailyTrainSeatSaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deleteDailyTrainSeat(@PathVariable Long id) {
        return dailyTrainSeatService.deleteDailyTrainSeat(id);
    }
}
