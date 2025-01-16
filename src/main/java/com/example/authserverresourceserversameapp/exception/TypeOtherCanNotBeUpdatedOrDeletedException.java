package com.example.authserverresourceserversameapp.exception;

public class TypeOtherCanNotBeUpdatedOrDeletedException extends RuntimeException{

    private final String message;

    public TypeOtherCanNotBeUpdatedOrDeletedException() {
        this.message = "Type \"Other\" can not be updated or deleted!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
