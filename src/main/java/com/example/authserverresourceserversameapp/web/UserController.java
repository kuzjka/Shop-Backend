package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.SuccessResponse;
import com.example.authserverresourceserversameapp.dto.UserDto;
import com.example.authserverresourceserversameapp.dto.Username;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.model.VerificationToken;
import com.example.authserverresourceserversameapp.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Calendar;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private static final String NOREPLY_ADDRESS = "anton30momot@gmail.com";
    private static final String APP_URL = "http://localhost:8080";
    private final UserService userService;
    private final JavaMailSender mailSender;

    @PostMapping
    @ResponseBody
    public SuccessResponse register(@RequestBody UserDto dto) throws MessagingException {
        String text = "Message for confirmation registration sand to your email";
        User registered = userService.registerNewUserAccount(dto);
        final MimeMessage email = constructEmailMessage(registered);
        mailSender.send(email);
        return new SuccessResponse(text);
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam String token) {
        VerificationToken verificationToken = userService.getToken(token);
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if (verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
            return "redirect:http://localhost:4200?token=" + token;
        }
        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return "redirect:http://localhost:4200";
    }

    @GetMapping("/resendRegistrationToken")
    @ResponseBody
    public SuccessResponse resendRegistrationToken(@RequestParam("token") final String existingToken)
            throws MessagingException {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        final User user = userService.getUser(newToken.getToken());
        final MimeMessage email = constructResetVerificationTokenEmail(newToken, user);
        mailSender.send(email);
        return new SuccessResponse("Message for confirmation registration sand to your email");
    }

    @GetMapping
    @ResponseBody
    public Username getUser(Principal principal) {
        if (principal == null) {
            return new Username("please log in", "");
        }
        User user = userService.findByUsername(principal.getName());
        String role = user.getRole().getName();
        return new Username(user.getUsername(), role);
    }

    @PutMapping
    @ResponseBody
    public SuccessResponse editUser(@RequestBody UserDto dto) {
        String text = "Password changed";
        userService.editExistingUserAccount(dto);
        return new SuccessResponse(text);
    }

    private MimeMessage constructEmailMessage(final User user) throws MessagingException {
        final String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);
        final String html = "<p><a href=\"" + APP_URL + "/user/registrationConfirm?token="
                + token + "\">Confirm registration</a></p>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(user.getEmail());
        helper.setSubject("Registration Confirmation");
        helper.setText(html, true);
        return message;
    }

    private MimeMessage constructResetVerificationTokenEmail(final VerificationToken newToken,
                                                             final User user) throws MessagingException {
        final String html = "<p><a href=\"" + APP_URL + "/user/registrationConfirm?token="
                + newToken.getToken() + "\">Confirm registration</a></p>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(user.getEmail());
        helper.setSubject("Resend Registration Token");
        helper.setText(html, true);
        return message;
    }
}
