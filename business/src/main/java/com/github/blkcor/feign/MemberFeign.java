package com.github.blkcor.feign;

import com.github.blkcor.req.MemberTicketSaveReq;
import com.github.blkcor.resp.CommonResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient("member")
public interface MemberFeign {
    @PostMapping("/ticket/save")
    CommonResp<Void> save(@RequestBody MemberTicketSaveReq req);
}
