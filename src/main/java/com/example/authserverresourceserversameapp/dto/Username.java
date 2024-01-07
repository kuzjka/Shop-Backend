package com.example.authserverresourceserversameapp.dto;

import java.util.List;

public class Username {
    private String username;
    private List<String> roles;

    public Username() {
    }

    public Username(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
