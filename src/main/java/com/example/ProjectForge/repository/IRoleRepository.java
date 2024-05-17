package com.example.ProjectForge.repository;

import com.example.ProjectForge.model.Role;

import java.util.List;

public interface IRoleRepository {

    //Get all roles from role table
    public List<Role> getAllRoles();
}
