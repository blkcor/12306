package com.github.blkcor.controller;

import com.github.blkcor.feign.BusinessFeign;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private BusinessFeign businessFeign;
        @RequestMapping("/hello")
        public String test() {
            return businessFeign.hello();
        }
}
