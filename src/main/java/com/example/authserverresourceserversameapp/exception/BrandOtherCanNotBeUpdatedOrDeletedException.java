package com.example.authserverresourceserversameapp.exception;

public class BrandOtherCanNotBeUpdatedOrDeletedException extends RuntimeException{

    private final String message;

    public BrandOtherCanNotBeUpdatedOrDeletedException() {

        this.message = "Brand \"Other\" can not be updated or deleted!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
