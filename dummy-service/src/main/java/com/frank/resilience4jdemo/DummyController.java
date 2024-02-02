package com.frank.resilience4jdemo;

import com.frank.resilience4jdemo.service.DummyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DummyController {

  private final DummyService dummyService;

  @GetMapping("/dummy")
  public String getDummy(@RequestParam int count) {
    log.info("getDummy called with count={}", count);
    return dummyService.getRandomString(count);
  }
}
