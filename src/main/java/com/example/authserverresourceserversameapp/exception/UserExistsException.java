package com.example.authserverresourceserversameapp.exception;

public class UserExistsException extends RuntimeException{

    private String message;

    public UserExistsException(String message) {
        this.message = message;

    }

    public String getMessage() {
        return message;
    }
}
