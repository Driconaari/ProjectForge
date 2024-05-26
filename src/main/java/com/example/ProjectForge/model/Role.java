package com.example.ProjectForge.model;

public class Role {

    private int role_id;
    private String role_name;

    public Role(int i, String admin) {
        this.role_id = role_id;
        this.role_name = role_name;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public void setId(int i) {
        this.role_id = i;
    }

    public void setName(String admin) {
        this.role_name = admin;
    }

    public Object getId() {
        return this.role_id;
    }

    public Object getName() {
        return this.role_name;
    }
}
