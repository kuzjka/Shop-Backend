package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.RegisterDto;
import com.example.authserverresourceserversameapp.model.User;


public interface UserService {

    User addUser(RegisterDto dto);
}



