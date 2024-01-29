package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.UserDto;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.model.VerificationToken;
import jakarta.mail.MessagingException;


public interface UserService {
    User getUser(String verificationToken);
    User registerNewUserAccount(UserDto accountDto) throws MessagingException;

    User editExistingUserAccount(UserDto dto);

    VerificationToken getToken(String token);

    void createVerificationTokenForUser(User user, String token);

    User getUserByVerificationToken(String verificationToken);

    User findByUsername(String username);

    VerificationToken generateNewVerificationToken(String existingVerificationToken) throws MessagingException;

    void saveRegisteredUser(User user);
}



