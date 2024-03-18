package com.example.authserverresourceserversameapp.exception;


public class TypeOtherCanNotBeDeletedOrUpdatedException extends RuntimeException {

    private String message;

    public TypeOtherCanNotBeDeletedOrUpdatedException() {
        this.message = "Type \"Other\" can't be deleted or updated!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
