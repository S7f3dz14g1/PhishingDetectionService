package com.szwedo.krystian.pds.dto;

import lombok.Data;

@Data
public class SubscriptionRequest {
    private String recipient;
    private String message;
}
