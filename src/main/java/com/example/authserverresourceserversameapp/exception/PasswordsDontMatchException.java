package com.example.authserverresourceserversameapp.exception;




public class PasswordsDontMatchException extends RuntimeException {

    private  String message;

    public PasswordsDontMatchException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
