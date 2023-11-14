package com.example.authserverresourceserversameapp.exception;


public class TypeOtherCantBeDeletedException extends RuntimeException {

    private String message;

    public TypeOtherCantBeDeletedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
