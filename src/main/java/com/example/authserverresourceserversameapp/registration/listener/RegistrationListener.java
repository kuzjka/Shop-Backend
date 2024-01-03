package com.example.authserverresourceserversameapp.registration.listener;

import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.registration.OnRegistrationCompleteEvent;
import com.example.authserverresourceserversameapp.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private static final String NOREPLY_ADDRESS = "anton30momot@gmail.com";
    private final UserService service;
    private final JavaMailSender mailSender;

    public RegistrationListener(UserService service, JavaMailSender mailSender) {
        this.service = service;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        try {
            this.confirmRegistration(event);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) throws MessagingException {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);
        final MimeMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    private MimeMessage constructEmailMessage(final OnRegistrationCompleteEvent event,
                                              final User user,
                                              final String token) throws MessagingException {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String html = "<p><a href=\"" + event.getAppUrl() + "/user/registrationConfirm?token="
                + token + "\">Confirm registration</a></p>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(recipientAddress);
        helper.setSubject(subject);
        helper.setText(html, true);
        return message;
    }
}
