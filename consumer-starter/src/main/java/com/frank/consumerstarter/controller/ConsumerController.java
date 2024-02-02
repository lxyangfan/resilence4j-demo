package com.frank.consumerstarter.controller;

import com.frank.resilience4jdemo.client.DummyServiceFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consumer")
@RequiredArgsConstructor
@Slf4j
public class ConsumerController {

    private final DummyServiceFeign dummyServiceFeign;

    @GetMapping("/hello")
    public String hello(@RequestParam int count) {
        log.info("call hello, count-> {}", count);
        return dummyServiceFeign.getDummy(count);
    }

}
