package com.frank.resilience4jdemo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dummyServiceClient", url = "http://localhost:8080")
public interface DummyServiceFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/api/dummy")
    String getDummy(@RequestParam int count);

}
