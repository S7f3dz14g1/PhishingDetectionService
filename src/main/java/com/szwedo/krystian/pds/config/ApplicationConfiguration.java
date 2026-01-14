package com.szwedo.krystian.pds.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
class ApplicationConfiguration {

    @Bean
    ObjectMapper getObjectMapper() {

        return new ObjectMapper();
    }

    @Bean
    HttpClient getHttpClient() {

        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))
                .build();
    }
}
