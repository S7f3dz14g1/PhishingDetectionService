package com.szwedo.krystian.pds.service;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UrlService {

    private static final Pattern URL_PATTERN = Pattern.compile("https?://\\S+");

    Optional<String> extractUrl(String message) {

        Matcher matcher = URL_PATTERN.matcher(message);
        return matcher.find()
                ? Optional.of(matcher.group())
                : Optional.empty();
    }
}
