package com.github.blkcor.controller.admin;

import com.github.blkcor.req.DailyTrainCarriageQueryReq;
import com.github.blkcor.req.DailyTrainCarriageSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainCarriageQueryResp;
import com.github.blkcor.service.DailyTrainCarriageService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/dailyTrainCarriage")
public class AdminDailyTrainCarriageController {
    @Resource
    private DailyTrainCarriageService dailyTrainCarriageService;

    @PostMapping("/save")
    public CommonResp<Void> saveDailyTrainCarriage(@RequestBody  @Valid DailyTrainCarriageSaveReq dailyTrainCarriageSaveReq) {
        return dailyTrainCarriageService.saveDailyTrainCarriage(dailyTrainCarriageSaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainCarriageQueryResp>> queryDailyTrainCarriageList(@Valid DailyTrainCarriageQueryReq dailyTrainCarriageQueryReq) {
        return dailyTrainCarriageService.queryDailyTrainCarriageList(dailyTrainCarriageQueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> updateDailyTrainCarriage(@RequestBody @Valid DailyTrainCarriageSaveReq dailyTrainCarriageSaveReq) {
        return dailyTrainCarriageService.saveDailyTrainCarriage(dailyTrainCarriageSaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deleteDailyTrainCarriage(@PathVariable Long id) {
        return dailyTrainCarriageService.deleteDailyTrainCarriage(id);
    }
}
