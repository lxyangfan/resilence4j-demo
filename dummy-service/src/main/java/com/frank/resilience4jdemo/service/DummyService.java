package com.frank.resilience4jdemo.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DummyService {

//  @CircuitBreaker(name = "dummyService")
  @CircuitBreaker(name = "dummyService", fallbackMethod = "fallback")
  public String getRandomString(int count) {
    try {
      if (count % 5 == 0) {
        throw new RuntimeException("Just fails");
      }
      Thread.sleep(count);
      return "Hello World!";
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public String fallback(int count, Throwable t) {
    log.warn("fallback called for count={} err {}", count, t.getMessage());
    return "fallback";
  }
}
