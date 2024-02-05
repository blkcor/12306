package com.github.blkcor.controller.admin;

import com.github.blkcor.req.DailyTrainTicketQueryReq;
import com.github.blkcor.req.DailyTrainTicketSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainTicketQueryResp;
import com.github.blkcor.service.DailyTrainTicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/dailyTrainTicket")
public class AdminDailyTrainTicketController {
    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    @PostMapping("/save")
    public CommonResp<Void> saveDailyTrainTicket(@RequestBody  @Valid DailyTrainTicketSaveReq dailyTrainTicketSaveReq) {
        return dailyTrainTicketService.saveDailyTrainTicket(dailyTrainTicketSaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainTicketQueryResp>> queryDailyTrainTicketList(@Valid DailyTrainTicketQueryReq dailyTrainTicketQueryReq) {
        return dailyTrainTicketService.queryDailyTrainTicketList(dailyTrainTicketQueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> updateDailyTrainTicket(@RequestBody @Valid DailyTrainTicketSaveReq dailyTrainTicketSaveReq) {
        return dailyTrainTicketService.saveDailyTrainTicket(dailyTrainTicketSaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deleteDailyTrainTicket(@PathVariable Long id) {
        return dailyTrainTicketService.deleteDailyTrainTicket(id);
    }
}
