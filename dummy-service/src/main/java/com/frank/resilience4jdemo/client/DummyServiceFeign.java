package com.frank.resilience4jdemo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "dummy-service",
    url = "${dummy-service.url:http://localhost:18080}",
    fallback = DummyServiceFeignFallback.class)
public interface DummyServiceFeign {

  @RequestMapping(method = RequestMethod.GET, value = "/api/dummy")
  String getDummy(@RequestParam int count);


}
