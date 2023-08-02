package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.repository.UserRepository;
import com.example.authserverresourceserversameapp.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
private UserService userService;

    public ProductController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/books")
    public String[] getArticles() {
        return new String[]{"Book 1", "Book 2", "Book 3"};
    }

    @PostMapping("/user")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }
}
