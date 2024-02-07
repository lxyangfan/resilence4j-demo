package com.frank.resilience4jdemo.client;

import org.springframework.stereotype.Component;

@Component
public class DummyServiceFeignFallback implements DummyServiceFeign {

  @Override
  public String getDummy(int count) {
    return "Fallback";
  }

}
