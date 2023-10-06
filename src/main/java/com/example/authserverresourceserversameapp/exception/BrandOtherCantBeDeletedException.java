package com.example.authserverresourceserversameapp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BrandOtherCantBeDeletedException extends RuntimeException{

    private final String message;
}
