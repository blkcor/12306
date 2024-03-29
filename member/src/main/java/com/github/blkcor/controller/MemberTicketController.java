package com.github.blkcor.controller;

import com.github.blkcor.context.LoginMemberContext;
import com.github.blkcor.req.MemberTicketQueryReq;
import com.github.blkcor.req.MemberTicketSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.MemberTicketQueryResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.service.MemberTicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/memberTicket")
public class MemberTicketController {
    @Resource
    private MemberTicketService memberTicketService;


    @GetMapping("/query-list")
    public CommonResp<PageResp<MemberTicketQueryResp>> queryMemberTicketList(@Valid MemberTicketQueryReq memberTicketQueryReq) {
        memberTicketQueryReq.setMemberId(LoginMemberContext.getId());
        return memberTicketService.queryMemberTicketList(memberTicketQueryReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deleteMemberTicket(@PathVariable Long id) {
        return memberTicketService.deleteMemberTicket(id);
    }
}
