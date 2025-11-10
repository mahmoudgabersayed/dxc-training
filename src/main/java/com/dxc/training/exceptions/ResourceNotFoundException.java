package com.dxc.training.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomAppException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    @Override
    protected HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }
}
