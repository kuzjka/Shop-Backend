package com.example.authserverresourceserversameapp.exception;


public class RegistrationResponse {

    private String message;

    public RegistrationResponse(String message) {
        this.message = "User with username \"" + message + "\" successfully created!";
    }


    public String getMessage() {
        return message;
    }


}
