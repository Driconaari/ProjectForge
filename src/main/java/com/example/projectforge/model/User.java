package com.example.projectforge.model;



public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String roles;
    // Constructors
    public User() {
    }

    public User(Long id, String username, String email,String password,String roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getRoles() {
        return roles;
    }

    // Additional methods as needed
}
