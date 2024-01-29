package com.github.blkcor.controller.admin;

import com.github.blkcor.req.DailyTrainQueryReq;
import com.github.blkcor.req.DailyTrainSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainQueryResp;
import com.github.blkcor.service.DailyTrainService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/dailyTrain")
public class AdminDailyTrainController {
    @Resource
    private DailyTrainService dailyTrainService;

    @PostMapping("/save")
    public CommonResp<Void> saveDailyTrain(@RequestBody  @Valid DailyTrainSaveReq dailyTrainSaveReq) {
        return dailyTrainService.saveDailyTrain(dailyTrainSaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainQueryResp>> queryDailyTrainList(@Valid DailyTrainQueryReq dailyTrainQueryReq) {
        return dailyTrainService.queryDailyTrainList(dailyTrainQueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> updateDailyTrain(@RequestBody @Valid DailyTrainSaveReq dailyTrainSaveReq) {
        return dailyTrainService.saveDailyTrain(dailyTrainSaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deleteDailyTrain(@PathVariable Long id) {
        return dailyTrainService.deleteDailyTrain(id);
    }
}
