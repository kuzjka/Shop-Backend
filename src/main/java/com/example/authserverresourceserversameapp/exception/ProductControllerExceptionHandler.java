package com.example.authserverresourceserversameapp.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ProductControllerExceptionHandler {
    @ExceptionHandler(UserExistsException.class)
    @ResponseBody
    public ErrorResponse handleUserAlreadyExistsException(UserExistsException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    @ResponseBody
    public ErrorResponse handlePasswordsDonNotMatchException(PasswordsDoNotMatchException ex) {
        return new ErrorResponse(ex.getMessage());
    }


}
