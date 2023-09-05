package com.example.authserverresourceserversameapp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PasswordsDoNotMatchException extends RuntimeException{
    private String message;
    public PasswordsDoNotMatchException() {
        this.message = "passwords do not match!";
    }
}
