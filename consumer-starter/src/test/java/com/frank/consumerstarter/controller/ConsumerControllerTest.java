package com.frank.consumerstarter.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ControllerTestConfiguration.class)
@ActiveProfiles("test")
class ConsumerControllerTest  {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Autowired private WireMockServer wireMockServer;

  @Autowired private ApplicationContext applicationContext;

  @Test
  void hello_use_java_configmock_success() {
    // arrange
    WireMock.configureFor(wireMockServer.port());
    WireMock.stubFor(
        WireMock.get(WireMock.urlPathMatching("/api/dummy"))
            .withQueryParam("count", WireMock.equalTo("123"))
            .willReturn(
                WireMock.aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "text/plain")
                    .withBody("mocked response")));

    // act
    ResponseEntity<String> response =
        restTemplate.getForEntity(
            "http://localhost:" + port + "/api/consumer/hello?count=123",
            String.class);
    // assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("mocked response", response.getBody());
  }


}
