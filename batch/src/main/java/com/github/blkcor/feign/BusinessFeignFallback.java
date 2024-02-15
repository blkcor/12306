package com.github.blkcor.feign;

import com.github.blkcor.resp.CommonResp;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BusinessFeignFallback implements BusinessFeign{
    @Override
    public String hello() {
        return "FallBack";
    }

    @Override
    public CommonResp<Void> genDaily(Date date) {
        return null;
    }
}
