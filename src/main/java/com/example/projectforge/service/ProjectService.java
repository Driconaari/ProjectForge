package com.example.projectforge.service;

import com.example.projectforge.dto.ProjectDto;
import com.example.projectforge.model.Project;
import com.example.projectforge.model.User;
import com.example.projectforge.projectRepository.ProjectRepository;
import com.example.projectforge.projectRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public void createProject(ProjectDto projectDto) {
        Project newProject = new Project();
        newProject.setProjectName(projectDto.getProjectName());
        newProject.setDescription(projectDto.getDescription());
        newProject.setDeadline(projectDto.getDeadline());

        // Get the currently logged-in user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        newProject.setUser(user);

        projectRepository.save(newProject);
    }

    public Object getUserProjects(User user) {
        return projectRepository.findByUser(user);
    }
}
