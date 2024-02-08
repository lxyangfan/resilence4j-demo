package com.frank.resilience4jdemo;

import com.frank.resilience4jdemo.service.DummyService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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
  @RateLimiter(name = "dummyService") // 限制访问的并发数，超过限制则触发异常
//  @CircuitBreaker(name = "dummyService") // 当服务调用失败次数达到阈值时，熔断器打开，后续请求直接报错
  @GetMapping()
  public String getDummy(@RequestParam int count) {
    log.debug("getDummy called with count={}", count);
    return dummyService.getRandomString(count);
  }
}
