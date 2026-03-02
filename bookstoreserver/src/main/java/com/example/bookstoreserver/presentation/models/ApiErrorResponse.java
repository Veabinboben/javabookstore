package com.example.bookstoreserver.presentation.models;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
    private Instant timestamp = Instant.now();
    private int status;
    private String title;
    private String instance;

    public ApiErrorResponse(int status, String title, String path) {
        this.status = status;
        this.title = title;
        this.instance = path;
    }

    public String getTitle() {
        return title;
    }

    public String getInstance() {
        return instance;
    }

    public int getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}