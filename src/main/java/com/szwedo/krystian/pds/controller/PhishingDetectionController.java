package com.szwedo.krystian.pds.controller;

import com.szwedo.krystian.pds.dto.PhishingRequest;
import com.szwedo.krystian.pds.dto.SubscriptionRequest;
import com.szwedo.krystian.pds.service.PhishingDetectionService;
import com.szwedo.krystian.pds.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/sms")
@RequiredArgsConstructor
class PhishingDetectionController {

    private final PhishingDetectionService phishingDetectionService;
    private final SubscriptionService subscriptionService;

    @PostMapping("/subscription")
    ResponseEntity<String> handleSubscription(@RequestBody SubscriptionRequest request) {
        subscriptionService.handleCommand(request.getRecipient(), request.getMessage());
        return ResponseEntity.ok("Subscription command processed for: " + request.getRecipient());
    }

    @PostMapping("/process")
    ResponseEntity<String> isPhishing(@RequestBody PhishingRequest request) {

        if (subscriptionService.isSubscribed(request.getRecipient())) {
            boolean isPhishing = phishingDetectionService.containsPhishing(request.getMessage());
            if (isPhishing) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Rejected: Phishing detected in message to protected user.");
            }
        }
        return ResponseEntity.ok("SMS accepted for delivery");
    }
}

