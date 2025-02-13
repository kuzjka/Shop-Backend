package com.example.authserverresourceserversameapp.exception;

public class BrandNotSelectedCanNotBeUpdatedOrDeletedException extends RuntimeException{

    private final String message;

    public BrandNotSelectedCanNotBeUpdatedOrDeletedException() {

        this.message = "Brand \"Not selected\" can not be updated or deleted!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
