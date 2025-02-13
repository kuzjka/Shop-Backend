package com.example.authserverresourceserversameapp.exception;

public class TypeNotSelectedCanNotBeUpdatedOrDeletedException extends RuntimeException{

    private final String message;

    public TypeNotSelectedCanNotBeUpdatedOrDeletedException() {
        this.message = "Type \"Not selected\" can not be updated or deleted!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
