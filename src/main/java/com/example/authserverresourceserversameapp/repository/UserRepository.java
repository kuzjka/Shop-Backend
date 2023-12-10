package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByUsername(String username);
User getByPassword(String password);
    User getByEmail(String email);
}
