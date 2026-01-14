package com.szwedo.krystian.pds.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Message {
    START("START"), STOP("STOP");
    private final String message;
}
