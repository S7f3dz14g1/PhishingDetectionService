package com.szwedo.krystian.pds.dto;

import lombok.Data;

@Data
public class PhishingRequest {
    private String sender;
    private String recipient;
    private String message;
}
