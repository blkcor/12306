package com.github.blkcor.controller;

import com.github.blkcor.req.TrainSeatQueryReq;
import com.github.blkcor.req.TrainSeatSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.TrainSeatQueryResp;
import com.github.blkcor.service.TrainSeatService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/trainSeat")
public class TrainSeatController {
    @Resource
    private TrainSeatService trainSeatService;

    @PostMapping("/save")
    public CommonResp<Void> saveTrainSeat(@RequestBody  @Valid TrainSeatSaveReq trainSeatSaveReq) {
        return trainSeatService.saveTrainSeat(trainSeatSaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<TrainSeatQueryResp>> queryTrainSeatList(@Valid TrainSeatQueryReq trainSeatQueryReq) {
        return trainSeatService.queryTrainSeatList(trainSeatQueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> updateTrainSeat(@RequestBody @Valid TrainSeatSaveReq trainSeatSaveReq) {
        return trainSeatService.saveTrainSeat(trainSeatSaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deleteTrainSeat(@PathVariable Long id) {
        return trainSeatService.deleteTrainSeat(id);
    }
}
