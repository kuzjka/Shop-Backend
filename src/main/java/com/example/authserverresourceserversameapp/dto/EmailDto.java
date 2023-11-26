package com.example.authserverresourceserversameapp.dto;

public class EmailDto {
    private String message;

    public EmailDto() {
    }

    public EmailDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
