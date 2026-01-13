package com.szwedo.krystian.pds.service;

import com.szwedo.krystian.pds.client.PhishingDetectionClient;
import com.szwedo.krystian.pds.model.VerifiedUrl;
import com.szwedo.krystian.pds.repository.VerifiedUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhishingDetectionService {

    private final PhishingDetectionClient phishingDetectionClient;
    private final UrlService urlService;
    private final VerifiedUrlRepository repository;


    @Cacheable(value = "phishingResults", key = "#url")
    public boolean containsPhishing(String message) {

        return message != null && repository.findById(message)
                .map(VerifiedUrl::isPhishing).orElseGet(() ->
                        urlService.extractUrl(message)
                                .map(url -> {
                                    boolean isPhishing = phishingDetectionClient.isPhishingUrl(url);
                                    repository.save(new VerifiedUrl(url, isPhishing));
                                    return isPhishing;
                                })
                                .orElse(false));
    }
}
