package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.UserDto;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.model.VerificationToken;


public interface UserService {
    User getUser(String verificationToken);
    User registerNewUserAccount(UserDto accountDto);

    User editExistingUserAccount(UserDto dto);

    VerificationToken getToken(String token);

    void createVerificationTokenForUser(User user, String token);

    User getUserByVerificationToken(String verificationToken);

    User findByUsername(String username);

    VerificationToken generateNewVerificationToken(String existingVerificationToken);

    void saveRegisteredUser(User user);
}



