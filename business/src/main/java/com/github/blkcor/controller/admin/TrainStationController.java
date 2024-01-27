package com.github.blkcor.controller.admin;

import com.github.blkcor.req.TrainStationQueryReq;
import com.github.blkcor.req.TrainStationSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.TrainStationQueryResp;
import com.github.blkcor.service.TrainStationService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/trainStation")
public class TrainStationController {
    @Resource
    private TrainStationService trainStationService;

    @PostMapping("/save")
    public CommonResp<Void> saveTrainStation(@RequestBody  @Valid TrainStationSaveReq trainStationSaveReq) {
        return trainStationService.saveTrainStation(trainStationSaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<TrainStationQueryResp>> queryTrainStationList(@Valid TrainStationQueryReq trainStationQueryReq) {
        return trainStationService.queryTrainStationList(trainStationQueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> updateTrainStation(@RequestBody @Valid TrainStationSaveReq trainStationSaveReq) {
        return trainStationService.saveTrainStation(trainStationSaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deleteTrainStation(@PathVariable Long id) {
        return trainStationService.deleteTrainStation(id);
    }
}
