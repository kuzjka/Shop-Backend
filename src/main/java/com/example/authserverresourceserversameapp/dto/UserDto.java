package com.example.authserverresourceserversameapp.dto;

import lombok.Data;

@Data
public class UserDto {
    private String role;
    private String username;
    private String email;
    private String oldPassword;
    private String password;
    private String passwordConfirmed;
}
