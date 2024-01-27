package com.github.blkcor.controller.admin;

import com.github.blkcor.req.TrainCarriageQueryReq;
import com.github.blkcor.req.TrainCarriageSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.TrainCarriageQueryResp;
import com.github.blkcor.service.TrainCarriageService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/trainCarriage")
public class AdminTrainCarriageController {
    @Resource
    private TrainCarriageService trainCarriageService;

    @PostMapping("/save")
    public CommonResp<Void> saveTrainCarriage(@RequestBody  @Valid TrainCarriageSaveReq trainCarriageSaveReq) {
        return trainCarriageService.saveTrainCarriage(trainCarriageSaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<TrainCarriageQueryResp>> queryTrainCarriageList(@Valid TrainCarriageQueryReq trainCarriageQueryReq) {
        return trainCarriageService.queryTrainCarriageList(trainCarriageQueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> updateTrainCarriage(@RequestBody @Valid TrainCarriageSaveReq trainCarriageSaveReq) {
        return trainCarriageService.saveTrainCarriage(trainCarriageSaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deleteTrainCarriage(@PathVariable Long id) {
        return trainCarriageService.deleteTrainCarriage(id);
    }
}
