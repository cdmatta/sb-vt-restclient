package com.example.frontapp;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.HttpClient;
import org.springframework.http.client.JettyClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FrontController {

    private final RestClient restClient;

    public FrontController(RestClient.Builder restClientBuilder) {
        var httpClient = new HttpClient();
        httpClient.setMaxConnectionsPerDestination(1024);
        httpClient.setMaxRequestsQueuedPerDestination(1024);
        var requestFactory = new JettyClientHttpRequestFactory(httpClient);
        this.restClient = restClientBuilder
                .baseUrl("http://localhost:8081")
                .requestFactory(requestFactory)
                .build();
        System.out.println(1);
    }

    @GetMapping("sleep5sec")
    public String simpleSleep() throws InterruptedException {
        var t = Thread.currentThread();
        TimeUnit.SECONDS.sleep(5);
        return "simpleSleep " + t;
    }

    @GetMapping("/callBackend")
    public String callBackend(@RequestParam(defaultValue = "5000") Long delayMillis) {
        return this.restClient.get().uri("/sleep/" + delayMillis).retrieve().body(String.class);
    }
}
