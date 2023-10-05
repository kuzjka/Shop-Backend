package com.example.authserverresourceserversameapp.exception;

public class RegistrationResponse {

    private  String message;

    public RegistrationResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
