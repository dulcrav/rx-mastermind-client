package com.chrosciu.rxmastermindclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private static final String SERVER_URL = "http://localhost:8080";

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(SERVER_URL).build();
    }

}
