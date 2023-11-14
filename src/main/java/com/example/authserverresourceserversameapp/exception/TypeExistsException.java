package com.example.authserverresourceserversameapp.exception;


public class TypeExistsException extends RuntimeException {
    private String message;

    public TypeExistsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
