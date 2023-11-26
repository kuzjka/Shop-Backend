package com.example.authserverresourceserversameapp.service;

public interface EmailService {

    void sendHtmlMessage(String to, String subject, String token);


}
