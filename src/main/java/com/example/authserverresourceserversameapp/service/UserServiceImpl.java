package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.UserDto;
import com.example.authserverresourceserversameapp.exception.PasswordsDontMatchException;
import com.example.authserverresourceserversameapp.exception.UserExistsException;
import com.example.authserverresourceserversameapp.exception.WrongPasswordException;
import com.example.authserverresourceserversameapp.model.Role;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.model.VerificationToken;
import com.example.authserverresourceserversameapp.repository.RoleRepository;
import com.example.authserverresourceserversameapp.repository.TokenRepository;
import com.example.authserverresourceserversameapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;

    @Override
    public User getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public User registerNewUserAccount(UserDto dto) {
        if (!dto.getPassword().equals(dto.getPasswordConfirmed())) {
            throw new PasswordsDontMatchException();
        }
        User user = new User();
        Role role;
        role = roleRepository.findAllByName(dto.getRole());
        if (role == null) {
            role = new Role();
        }
        role.setName(dto.getRole());
        role.addUser(user);
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (userRepository.getByUsername(dto.getUsername()) != null) {
            throw new UserExistsException("User with username: \"" + dto.getUsername() + "\" already exists!");
        }
        if (userRepository.getByEmail(dto.getEmail()) != null) {
            throw new UserExistsException("User with email: \"" + dto.getEmail() + "\" already exists!");
        }
        return userRepository.save(user);
    }

    @Override
    public User editExistingUserAccount(UserDto dto) {
        if (!dto.getPassword().equals(dto.getPasswordConfirmed())) {
            throw new PasswordsDontMatchException();
        }
        User user = userRepository.getByUsername(dto.getUsername());
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public VerificationToken getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public User getUserByVerificationToken(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID()
                .toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }
}
