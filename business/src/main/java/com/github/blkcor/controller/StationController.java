package com.github.blkcor.controller;

import com.github.blkcor.req.StationQueryReq;
import com.github.blkcor.req.StationSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.StationQueryResp;
import com.github.blkcor.service.StationService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/station")
public class StationController {
    @Resource
    private StationService stationService;

    @PostMapping("/save")
    public CommonResp<Void> saveStation(@RequestBody  @Valid StationSaveReq stationSaveReq) {
        return stationService.saveStation(stationSaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<StationQueryResp>> queryStationList(@Valid StationQueryReq stationQueryReq) {
        return stationService.queryStationList(stationQueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> updateStation(@RequestBody @Valid StationSaveReq stationSaveReq) {
        return stationService.saveStation(stationSaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deleteStation(@PathVariable Long id) {
        return stationService.deleteStation(id);
    }
}
