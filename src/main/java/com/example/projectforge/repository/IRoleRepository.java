package com.example.projectforge.repository;

import com.example.projectforge.model.Role;

import java.util.List;

public interface IRoleRepository {

    //Get all roles
    public List<Role> getAllRoles();
}
