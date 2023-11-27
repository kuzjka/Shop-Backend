package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.model.VerificationToken;
import com.example.authserverresourceserversameapp.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String NOREPLY_ADDRESS = "noreply@test.com";
    private JavaMailSender emailSender;
    private UserService userService;

    public EmailServiceImpl(JavaMailSender emailSender, UserService userService) {
        this.emailSender = emailSender;
        this.userService = userService;
    }



    public void sendHtmlMessage(User user) {
        String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);

        final String html = "<p><a href=\"http://localhost:8080/registrationConfirm?token=" + token + "\">Confirm registration</a></p>";
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(NOREPLY_ADDRESS);
            helper.setTo("123@456");
            helper.setSubject("confirm registration");
            helper.setText(html, true);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
