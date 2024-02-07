package com.frank.consumerstarter.controller;

import com.frank.resilience4jdemo.client.DummyServiceFeign;
import com.github.tomakehurst.wiremock.WireMockServer;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration
@ActiveProfiles("test")
@Slf4j
public class ControllerTestConfiguration {

  @Autowired private ConfigurableEnvironment environment;

  // 使用 wiremock 伪造服务
  @Bean(destroyMethod = "stop")
  public WireMockServer wireMockServer(RefreshFeignUrl refreshFeignUrl) {
    WireMockServer server = new WireMockServer(0); // 0 means random port
    server.start();

    int port = server.port();
    String url = buildUrl(port);
    log.info("WireMockServer url: {}", url);

    updateConfigProperty("spring.cloud.openfeign.client.config.dummy-service.url", url);

    refreshFeignUrl.refreshFeignUrl();
    return server;
  }

  private String buildUrl(int port) {
    return "http://localhost:" + port;
  }

  private void updateConfigProperty(String propertyName, String propertyValue) {
    MutablePropertySources propertySources = environment.getPropertySources();
    Map myMap = new HashMap();
    myMap.put(propertyName, propertyValue);
    propertySources.addLast(new MapPropertySource("MY_MAP", myMap));
  }

  @Component
  static class RefreshFeignUrl implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      this.applicationContext = applicationContext;
    }

    public void refreshFeignUrl() {
      applicationContext.publishEvent(new RefreshEvent(this, (Object) null, "Refresh Feign url"));
    }

  }
}
