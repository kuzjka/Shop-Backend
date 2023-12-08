package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.UserDto;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.model.VerificationToken;


public interface UserService {

    User registerNewUserAccount(UserDto accountDto);

    VerificationToken getToken(String token);

    void createVerificationTokenForUser(User user, String token);

    User getUser(String verificationToken);

    VerificationToken generateNewVerificationToken(String existingVerificationToken);

    void saveRegisteredUser(User user);
}



