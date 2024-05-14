package com.example.projectforge.service;

import com.example.projectforge.model.Project;
import com.example.projectforge.repository.ProjectRepository;
import com.example.projectforge.userRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

@Override
public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    com.example.projectforge.model.User user = userRepository.findById(userId);
    if (user != null) {
        System.out.println("User not found in the database");
        throw new UsernameNotFoundException("User not found");
    }
    System.out.println("User found in the database: " + user.getUsername());

    // Assuming you have a method in your UserRepository to get the role name by role_id
    String roleName = userRepository.findRoleNameByRoleId(user.getRole_id());

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(roleName));

    List<String> authorityNames = new ArrayList<>();
    for (GrantedAuthority authority : authorities) {
        authorityNames.add(authority.getAuthority());
    }
    user.setAuthorities(authorityNames); // Set the authorities to the user

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
}

    public long getUserID(int projectId) {
        Project project = projectRepository.findById(projectId);
        if (project != null) {
            com.example.projectforge.model.User user = userRepository.findById(String.valueOf(project.getUser_id()));
            if (user != null) {
                return user.getUserId();
            }
        }
        throw new UsernameNotFoundException("Project not found"); // Or return a default user ID
    }
}