package com.example.authserverresourceserversameapp.exception;


public class BrandOtherCantBeDeletedException extends RuntimeException {

    private String message;

    public BrandOtherCantBeDeletedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
