package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.model.VerificationToken;

public interface EmailService {

    void sendVerificationTokenHtmlMessage(User user);
    void resendVerificationTokenHtmlMessage(VerificationToken newToken);

}
