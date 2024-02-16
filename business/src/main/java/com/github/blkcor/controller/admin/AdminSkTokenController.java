package com.github.blkcor.controller.admin;

import com.github.blkcor.req.SkTokenQueryReq;
import com.github.blkcor.req.SkTokenSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.SkTokenQueryResp;
import com.github.blkcor.service.SkTokenService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/admin/skToken")
public class AdminSkTokenController {
    @Resource
    private SkTokenService skTokenService;

    @PostMapping("/save")
    public CommonResp<Void> saveSkToken(@RequestBody @Valid SkTokenSaveReq skTokenSaveReq) {
        return skTokenService.saveSkToken(skTokenSaveReq);
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<SkTokenQueryResp>> querySkTokenList(@Valid SkTokenQueryReq skTokenQueryReq) {
        return skTokenService.querySkTokenList(skTokenQueryReq);
    }

    @PutMapping("/update")
    public CommonResp<Void> updateSkToken(@RequestBody @Valid SkTokenSaveReq skTokenSaveReq) {
        return skTokenService.saveSkToken(skTokenSaveReq);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Void> deleteSkToken(@PathVariable Long id) {
        return skTokenService.deleteSkToken(id);
    }
}
