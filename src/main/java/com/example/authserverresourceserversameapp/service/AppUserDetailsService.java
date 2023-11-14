package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.model.AppUser;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " does not exists");
        }
        return new AppUser(user);
    }
}
