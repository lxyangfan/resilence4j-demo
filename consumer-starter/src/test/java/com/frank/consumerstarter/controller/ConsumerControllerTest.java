package com.frank.consumerstarter.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@WireMockTest(
    proxyMode = true,
    httpPort = 18080) // simplifies configuration and supports multi-domain mocking.
class ConsumerControllerTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void hello_use_java_configmock_success() {
    // arrange
    stubFor(
        get(urlPathMatching("/api/dummy"))
            .withQueryParam("count", WireMock.matching("\\d+"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "text/plain")
                    .withBody("mocked response")));

    // act
    ResponseEntity<String> response =
        restTemplate.getForEntity(getServiceUrl() + "/api/consumer/hello?count=123", String.class);
    // assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("mocked response", response.getBody());
  }

  @Test
  void hello_with_response_body_file() {
    // arrange
    stubFor(
        get(urlPathMatching("/api/dummy"))
            .withQueryParam("count", WireMock.equalTo("123"))
            .willReturn(ok().withBodyFile("hello")));

    // act
    ResponseEntity<String> response =
        restTemplate.getForEntity(getServiceUrl() + "/api/consumer/hello?count=123", String.class);
    // assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("hello", response.getBody());
  }

  // 本服务启动的 port 是随机的，这里需要获取一下
  private String getServiceUrl() {
    return "http://localhost:" + port;
  }
}
