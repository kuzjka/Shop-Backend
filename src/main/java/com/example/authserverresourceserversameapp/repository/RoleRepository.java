package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findAllByName(String name);
}
