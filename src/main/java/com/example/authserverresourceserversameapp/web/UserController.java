package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.RegisterDto;
import com.example.authserverresourceserversameapp.dto.Username;
import com.example.authserverresourceserversameapp.exception.RegistrationResponse;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public Username getUser(Principal principal) {


        return new Username("User: " + principal.getName());
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegisterDto dto) {
        String username = userService.addUser(dto).getUsername();

        RegistrationResponse response = new RegistrationResponse("user with username: \"" + username + "\" successfully registered!");

        return ResponseEntity.ok(response);

    }
}
