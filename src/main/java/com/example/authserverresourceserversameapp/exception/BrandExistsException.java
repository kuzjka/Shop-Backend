package com.example.authserverresourceserversameapp.exception;


public class BrandExistsException extends RuntimeException {
    private String message;

    public BrandExistsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
