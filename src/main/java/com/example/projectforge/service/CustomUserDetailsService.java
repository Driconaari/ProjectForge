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
        com.example.projectforge.model.User user = userRepository.findById((long) Integer.parseInt(userId));
        if (user == null) {
            System.out.println("User not found in the database");
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("User found in the database: " + user.getUsername());

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getIsAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return (UserDetails) user; // Return the user object directly
    }

    public long getUserID(int projectId) {
        Project project = projectRepository.getProjectByProjectID(projectId);
        if (project != null) {
            return project.getUser_id();
        }
        return -1; // return -1 or throw an exception if the project is not found
    }
//
}