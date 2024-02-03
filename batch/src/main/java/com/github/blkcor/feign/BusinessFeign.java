package com.github.blkcor.feign;

import com.github.blkcor.resp.CommonResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@Component
@FeignClient(name = "business", url = "http://localhost:8889/business")
public interface BusinessFeign {
    @GetMapping("/test/hello")
    String hello();

    @GetMapping("/admin/dailyTrain/gen-daily/{date}")
    CommonResp<Void> genDaily(
            @PathVariable
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
}
