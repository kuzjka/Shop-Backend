package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.EmailDto;
import com.example.authserverresourceserversameapp.dto.UserDto;
import com.example.authserverresourceserversameapp.dto.Username;
import com.example.authserverresourceserversameapp.model.Role;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.model.VerificationToken;
import com.example.authserverresourceserversameapp.registration.OnRegistrationCompleteEvent;
import com.example.authserverresourceserversameapp.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final String NOREPLY_ADDRESS = "anton30momot@gmail.com";
    private final UserService userService;
    private final JavaMailSender mailSender;

    private final ApplicationEventPublisher eventPublisher;

    public UserController(UserService userService,
                          JavaMailSender mailSender,
                          ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.mailSender = mailSender;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/resendRegistrationToken")
    @ResponseBody
    public EmailDto resendRegistrationToken(final HttpServletRequest request,
                                            @RequestParam("token") final String existingToken)
            throws MessagingException {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        final User user = userService.getUser(newToken.getToken());
        final String appUrl = "http://" + request.getServerName() + ":"
                + request.getServerPort()
                + request.getContextPath();
        final MimeMessage email = constructResetVerificationTokenEmail(appUrl, newToken, user);
        mailSender.send(email);
        return new EmailDto("Message for confirmation registration sand to your email");
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

    @GetMapping
    @ResponseBody
    public Username getUser(Principal principal) {
        List<Role> roles = new ArrayList<>();
        List<String> roleNames = new ArrayList<>();
        if (principal == null) {
            return new Username("please log in", roleNames);
        }
        User user = userService.findByUsername(principal.getName());
        roles = user.getRoles();
        for (Role role : roles) {
            roleNames.add(role.getName());
        }

        return new Username(user.getUsername(), roleNames);
    }

    @PostMapping
    @ResponseBody
    public EmailDto register(@RequestBody UserDto dto, final HttpServletRequest request) {
        String text = "Message for confirmation registration sand to your email";
        final String appUrl = "http://"
                + request.getServerName() + ":"
                + request.getServerPort()
                + request.getContextPath();
        User registered = userService.registerNewUserAccount(dto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, appUrl));
        return new EmailDto(text);
    }

    @PutMapping
    @ResponseBody
    public EmailDto editUser(@RequestBody UserDto dto) {
        String text = "Password changed";
        userService.editExistingUserAccount(dto);
        return new EmailDto(text);
    }

    private MimeMessage constructResetVerificationTokenEmail(final String contextPath,
                                                             final VerificationToken newToken,
                                                             final User user) throws MessagingException {
        final String html = "<p><a href=\"" + contextPath + "/user/registrationConfirm?token="
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
