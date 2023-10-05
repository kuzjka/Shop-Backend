package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.RegisterDto;
import com.example.authserverresourceserversameapp.exception.PasswordsDontMatchException;
import com.example.authserverresourceserversameapp.exception.UserExistsException;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Override
    public User addUser(RegisterDto dto) {
        if (!dto.getPassword().equals(dto.getPasswordConfirmed())) {
            throw new PasswordsDontMatchException("passwords don't match!");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (userRepository.getByUsername(dto.getUsername()) != null) {
            throw new UserExistsException("User with username: \"" + dto.getUsername() + "\" already exists!");
        }
        return userRepository.save(user);
    }
}
