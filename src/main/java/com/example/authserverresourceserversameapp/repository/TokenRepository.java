package com.example.authserverresourceserversameapp.repository;


import com.example.authserverresourceserversameapp.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

}
