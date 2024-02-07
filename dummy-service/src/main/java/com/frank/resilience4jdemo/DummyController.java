package com.frank.resilience4jdemo;

import com.frank.resilience4jdemo.service.DummyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dummy 服务
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/dummy")
public class DummyController {

  private final DummyService dummyService;

  /**
   * 获取随机字符串
   * @param count
   * @return
   */
  @GetMapping()
  public String getDummy(@RequestParam int count) {
    log.info("getDummy called with count={}", count);
    return dummyService.getRandomString(count);
  }
}
