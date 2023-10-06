package com.example.authserverresourceserversameapp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BrandExistsException extends RuntimeException {
    private final String message;
}
