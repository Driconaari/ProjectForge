package com.example.projectforge.dto;

public class UserRegistrationDto {
    private String username;
    private String password;

    // getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}