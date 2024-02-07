package com.frank.consumerstarter.controller;

import com.frank.resilience4jdemo.client.DummyServiceFeign;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消费服务
 */
@RestController
@RequestMapping("/api/consumer")
@RequiredArgsConstructor
@Slf4j
public class ConsumerController {

    private final DummyServiceFeign dummyServiceFeign;

    /**
     * 调用hello
     * @param count
     * @return
     */
    @RateLimiter(name="dummy")
    @GetMapping("/hello")
    public ResponseEntity<String> hello(@RequestParam int count) {
        log.info("call hello, count-> {}", count);
        String dummy = dummyServiceFeign.getDummy(count);
        if ("fallback".equals(dummy)) {
            return ResponseEntity.status(500).body(dummy);
        }
        return ResponseEntity.ok(dummy);
    }

}
