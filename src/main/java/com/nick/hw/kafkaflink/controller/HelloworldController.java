package com.nick.hw.kafkaflink.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloworldController {

    @RequestMapping("/hello")
    public String sayHello(@RequestParam String name) {
        return "hello from " + name;
    }
}
