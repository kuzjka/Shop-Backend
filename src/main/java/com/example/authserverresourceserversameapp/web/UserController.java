package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.EmailDto;
import com.example.authserverresourceserversameapp.dto.RegisterDto;
import com.example.authserverresourceserversameapp.dto.Username;
import com.example.authserverresourceserversameapp.service.EmailService;
import com.example.authserverresourceserversameapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Controller

public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private String token;
    private RegisterDto registerDto;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }


    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam String token) {
        if (this.token.equals(token)) {
            userService.addUser(this.registerDto);}
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
        this.registerDto = dto;
        this.token = UUID.randomUUID().toString();
        emailService.sendHtmlMessage("123@456", "confirm", token);
        return new EmailDto(text);
    }
}
