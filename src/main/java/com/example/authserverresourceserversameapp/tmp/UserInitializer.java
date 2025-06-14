package com.example.authserverresourceserversameapp.tmp;

import com.example.authserverresourceserversameapp.dto.UserDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.Role;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.repository.CartRepository;
import com.example.authserverresourceserversameapp.repository.RoleRepository;
import com.example.authserverresourceserversameapp.repository.UserRepository;
import com.example.authserverresourceserversameapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserInitializer {
    private static final Logger logger = LoggerFactory.getLogger(UserInitializer.class);
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    public UserInitializer(UserRepository userRepository, CartRepository cartRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void onAppInit(ApplicationEvent event) {
        Role role = roleRepository.findAllByName("admin");
        logger.info("Admin role ID: " + role.getId());

        User user = new User();
        user.setUsername("test");
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("secret"));
        user.setRole(roleRepository.findAllByName("admin"));
        user.setEnabled(true);
        user = userRepository.save(user);
        logger.info("Added user {} (ID: {})", user.getUsername(), user.getId());

        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
    }
}
