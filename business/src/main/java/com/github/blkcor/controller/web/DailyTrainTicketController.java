package com.github.blkcor.controller.web;

import com.github.blkcor.req.DailyTrainTicketQueryReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.DailyTrainTicketQueryResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.service.DailyTrainTicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/dailyTrainTicket")
public class DailyTrainTicketController {
    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainTicketQueryResp>> queryDailyTrainTicketList(@Valid DailyTrainTicketQueryReq dailyTrainTicketQueryReq) {
        return dailyTrainTicketService.queryDailyTrainTicketList(dailyTrainTicketQueryReq);
    }
}
