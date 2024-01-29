package com.github.blkcor.controller.admin;

import com.github.blkcor.req.DailyTrainStationQueryReq;
import com.github.blkcor.req.DailyTrainStationSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainStationQueryResp;
import com.github.blkcor.service.DailyTrainStationService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/dailyTrainStation")
public class AdminDailyTrainStationController {
    @Resource
    private DailyTrainStationService dailyTrainStationService;

    @PostMapping("/save")
    public CommonResp<Void> saveDailyTrainStation(@RequestBody  @Valid DailyTrainStationSaveReq dailyTrainStationSaveReq) {
        return dailyTrainStationService.saveDailyTrainStation(dailyTrainStationSaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainStationQueryResp>> queryDailyTrainStationList(@Valid DailyTrainStationQueryReq dailyTrainStationQueryReq) {
        return dailyTrainStationService.queryDailyTrainStationList(dailyTrainStationQueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> updateDailyTrainStation(@RequestBody @Valid DailyTrainStationSaveReq dailyTrainStationSaveReq) {
        return dailyTrainStationService.saveDailyTrainStation(dailyTrainStationSaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deleteDailyTrainStation(@PathVariable Long id) {
        return dailyTrainStationService.deleteDailyTrainStation(id);
    }
}
