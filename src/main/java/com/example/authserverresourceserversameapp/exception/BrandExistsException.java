package com.example.authserverresourceserversameapp.exception;


public class BrandExistsException extends RuntimeException {
    private final String message;

    public BrandExistsException(String name) {
        this.message = "Brand with name: \"" + name + "\" already exists!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
