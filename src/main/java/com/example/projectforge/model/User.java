package com.example.projectforge.model;

import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id; // Changed from id to user_id
    private String username;
    private String email;
    private String password;
    private String roles;

    @Column(name = "is_admin")
    private boolean isAdmin;

    // Constructors
    public User() {
    }

    public User(Long user_id, String username, String email, String password, String roles, boolean isAdmin) {
        this.user_id = user_id; // Changed from id to user_id
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.isAdmin = isAdmin;
    }

    // Getters and setters
    public Long getUser_id() { // Changed from getId to getUser_id
        return user_id; // Changed from id to user_id
    }

    public void setUser_id(Long user_id) { // Changed from setId to setUser_id
        this.user_id = user_id; // Changed from id to user_id
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}