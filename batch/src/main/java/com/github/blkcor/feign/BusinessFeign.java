package com.github.blkcor.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(name = "business",url = "http://localhost:8889/business")
public interface BusinessFeign {
    @GetMapping("/test/hello")
    String hello();
}
