package com.github.blkcor.controller;

import com.github.blkcor.context.LoginMemberContext;
import com.github.blkcor.req.${Domain}QueryReq;
import com.github.blkcor.req.${Domain}SaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.${Domain}QueryResp;
import com.github.blkcor.service.${Domain}Service;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/${do_main}")
public class ${Domain}Controller {
    @Resource
    private ${Domain}Service ${domain}Service;

    @PostMapping("/save")
    public CommonResp<Void> save${Domain}(@RequestBody  @Valid ${Domain}SaveReq ${domain}SaveReq) {
        return ${domain}Service.save${Domain}(${domain}SaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<${Domain}QueryResp>> query${Domain}List(@Valid ${Domain}QueryReq ${domain}QueryReq) {
        ${domain}QueryReq.setMemberId(LoginMemberContext.getId());
        return ${domain}Service.query${Domain}List(${domain}QueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> update${Domain}(@RequestBody @Valid ${Domain}SaveReq ${domain}SaveReq) {
        return ${domain}Service.save${Domain}(${domain}SaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> delete${Domain}(@PathVariable Long id) {
        return ${domain}Service.delete${Domain}(id);
    }
}
