package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.EmailDto;
import com.example.authserverresourceserversameapp.dto.RegisterDto;
import com.example.authserverresourceserversameapp.dto.Username;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.model.VerificationToken;
import com.example.authserverresourceserversameapp.service.EmailService;
import com.example.authserverresourceserversameapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam String token) {
        VerificationToken verificationToken = userService.getToken(token);
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return "redirect:http://localhost:4200";
    }

    @GetMapping("/user")
    @ResponseBody
    public Username getUser(Principal principal) {
        return new Username("User: " + principal.getName());
    }

    @PostMapping("/register")
    @ResponseBody
    public EmailDto register(@RequestBody RegisterDto dto) {
        String text = "Message for confirmation registration sand to your email";
        User registered = userService.registerNewUserAccount(dto);
        emailService.sendHtmlMessage(registered);
        return new EmailDto(text);
    }
}
