package com.szwedo.krystian.pds.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VerifiedUrl {
    @Id
    private String url;
    private boolean isPhishing;
    private LocalDateTime checkedAt;

    public VerifiedUrl(String url, boolean isPhishing) {
        this.url = url;
        this.isPhishing = isPhishing;
        this.checkedAt = LocalDateTime.now();
    }
}