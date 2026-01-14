package com.szwedo.krystian.pds.service;

import com.szwedo.krystian.pds.dto.Message;
import com.szwedo.krystian.pds.model.Subscription;
import com.szwedo.krystian.pds.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository repository;

    public void handleCommand(String phoneNumber, String messageContent) {

        String command = messageContent.trim().toUpperCase();
        switch (Message.valueOf(command.toUpperCase())) {
            case Message.START: {
                repository.save(new Subscription(phoneNumber));
                log.info("Subscription activated for phone number: {}", phoneNumber);
            }
            case Message.STOP: {
                repository.deleteById(phoneNumber);
                log.info("Subscription deactivated for phone number: {}", phoneNumber);
            }
            default:
                log.warn("Unknown command received from {}: {}", phoneNumber, command);
        }
    }

    public boolean isSubscribed(String phoneNumber) {
        boolean exists = repository.existsById(phoneNumber);
        log.debug("Checking subscription for {}: {}", phoneNumber, exists);
        return exists;
    }
}
