package com.example.authserverresourceserversameapp.exception;


public class ProductExistsException extends RuntimeException {

    private  String message;

    public ProductExistsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
