package com.example.authserverresourceserversameapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductControllerExceptionHandler {
    @ExceptionHandler(UserExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserAlreadyExistsException(UserExistsException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlePasswordsDonNotMatchException(PasswordsDoNotMatchException ex) {
        return new ErrorResponse(ex.getMessage());
    }


}
