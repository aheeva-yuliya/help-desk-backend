package com.javamaster.providers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class DateTimeProvider {
    public Timestamp now() {
        return Timestamp.from(Instant.now());
    }
}