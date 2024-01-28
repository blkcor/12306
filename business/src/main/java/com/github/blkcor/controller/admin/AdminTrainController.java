package com.github.blkcor.controller.admin;

import com.github.blkcor.req.TrainQueryReq;
import com.github.blkcor.req.TrainSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.TrainQueryResp;
import com.github.blkcor.service.TrainSeatService;
import com.github.blkcor.service.TrainService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/train")
public class AdminTrainController {
    @Resource
    private TrainService trainService;

    @Resource
    private TrainSeatService trainSeatService;

    @PostMapping("/save")
    public CommonResp<Void> saveTrain(@RequestBody  @Valid TrainSaveReq trainSaveReq) {
        return trainService.saveTrain(trainSaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<TrainQueryResp>> queryTrainList(@Valid TrainQueryReq trainQueryReq) {
        return trainService.queryTrainList(trainQueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> updateTrain(@RequestBody @Valid TrainSaveReq trainSaveReq) {
        return trainService.saveTrain(trainSaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deleteTrain(@PathVariable Long id) {
        return trainService.deleteTrain(id);
    }

    @GetMapping("/query-all")
    public CommonResp<List<TrainQueryResp>> queryAllTrainList() {
        return trainService.queryAllTrainList();
    }

    @GetMapping("/gen-seat/{trainCode}")
    public CommonResp<Void> genSeat(@PathVariable String trainCode) {
        return trainSeatService.genSeat(trainCode);
    }
}
