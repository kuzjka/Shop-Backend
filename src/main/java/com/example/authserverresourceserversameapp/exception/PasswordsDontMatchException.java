package com.example.authserverresourceserversameapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordsDontMatchException extends RuntimeException {

    private String message;


}
