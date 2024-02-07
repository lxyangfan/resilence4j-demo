package com.frank.consumerstarter.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration
@ActiveProfiles("test")
public class ControllerTestWithFixedPortConfiguration {

  @Autowired private ConfigurableEnvironment environment;

  // 使用 wiremock 伪造服务
  @Bean(destroyMethod = "stop")
  public WireMockServer wireMockServer() {
    WireMockServer server = new WireMockServer(18080); // 0 means random port
    server.start();
    return server;
  }

}
