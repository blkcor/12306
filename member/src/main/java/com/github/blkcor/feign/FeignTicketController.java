package com.github.blkcor.feign;

import com.github.blkcor.req.MemberTicketSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.service.MemberTicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign/ticket")
public class FeignTicketController {
    @Resource
    private MemberTicketService memberTicketService;
    @PostMapping("/save")
    public CommonResp<Void> save(@Valid @RequestBody MemberTicketSaveReq req){
        return memberTicketService.saveMemberTicket(req);
    }

}
