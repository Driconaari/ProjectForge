package com.example.ProjectForge.model;

public class User {

    private int user_id;
    private String username;
    private String password;
    private String email;
    private int role_id;

    public User(int user_id, String username, String password, String email) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(int user_id, String username, String password, String email, int role_id) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role_id = role_id;
    }

    public User(int user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }


    //Default constructor
    public User() {
    }

    // getters
    public String getEmail() {
        return email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    // setters

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
