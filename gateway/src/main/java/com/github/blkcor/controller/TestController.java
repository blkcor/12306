package com.github.blkcor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/gateWay")
    public String test() {
        return "test";
    }
}
