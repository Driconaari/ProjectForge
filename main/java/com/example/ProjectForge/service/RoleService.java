package com.example.ProjectForge.service;

import com.example.ProjectForge.model.Role;
import com.example.ProjectForge.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    //Get all roles
    public List<Role> getAllRoles() {
        return roleRepository.getAllRoles();
    }

}
