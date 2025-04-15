package com.starwars.exception;

import org.springframework.http.HttpStatus;

public class CustomizableException extends RuntimeException {
    private HttpStatus status;

    public CustomizableException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CustomizableException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}