package com.example.authserverresourceserversameapp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String NOREPLY_ADDRESS = "noreply@test.com";
    private JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendHtmlMessage(String to, String subject, String token) {


        final String html = "<p><a href=\"http://localhost:8080/registrationConfirm?token=" + token + "\">Confirm registration</a></p>";
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(NOREPLY_ADDRESS);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
