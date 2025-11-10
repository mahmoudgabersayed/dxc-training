package com.dxc.training.exceptions;

import org.springframework.http.HttpStatus;

public abstract class CustomAppException extends RuntimeException {
    public CustomAppException(String message) {
        super(message);
    }

    protected HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
