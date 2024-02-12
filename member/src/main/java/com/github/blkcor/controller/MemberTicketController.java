package com.github.blkcor.controller;

import com.github.blkcor.req.MemberTicketQueryReq;
import com.github.blkcor.req.MemberTicketSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.MemberTicketQueryResp;
import com.github.blkcor.service.MemberTicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/memberTicket")
public class MemberTicketController {
    @Resource
    private MemberTicketService memberTicketService;

    @PostMapping("/save")
    public CommonResp<Void> saveMemberTicket(@RequestBody  @Valid MemberTicketSaveReq memberTicketSaveReq) {
        return memberTicketService.saveMemberTicket(memberTicketSaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<MemberTicketQueryResp>> queryMemberTicketList(@Valid MemberTicketQueryReq memberTicketQueryReq) {
        return memberTicketService.queryMemberTicketList(memberTicketQueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> updateMemberTicket(@RequestBody @Valid MemberTicketSaveReq memberTicketSaveReq) {
        return memberTicketService.saveMemberTicket(memberTicketSaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deleteMemberTicket(@PathVariable Long id) {
        return memberTicketService.deleteMemberTicket(id);
    }
}
