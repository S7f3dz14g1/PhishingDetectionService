package com.szwedo.krystian.pds.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class PhishingDetectionClient {

    private static final String API_URL =
            "https://cloud.google.com/web-risk/docs/reference/rest/v1eap1/TopLevel/evaluateUri";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;


    public boolean isPhishingUrl(String url) {

        try {
            HttpRequest request = buildRequest(url);
            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return isSuccessful(response) && containsMaliciousFlag(response.body());

        } catch (Exception e) {
            log.error("Google Web Risk API error: ", e);
            return false;
        }
    }

    private HttpRequest buildRequest(String url) {

        String jsonBody = objectMapper.writeValueAsString(Map.of("uri", url));

        return HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
    }

    private boolean isSuccessful(HttpResponse<?> response) {

        return response.statusCode() == 200;
    }

    private boolean containsMaliciousFlag(String responseBody) {

        return responseBody.contains("\"malicious\": true");
    }
}
