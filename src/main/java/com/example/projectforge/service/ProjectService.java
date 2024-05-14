package com.example.projectforge.service;

import com.example.projectforge.dto.ProjectDto;
import com.example.projectforge.model.Project;
import com.example.projectforge.projectRepository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public void createProject(ProjectDto projectDto) {
        Project newProject = new Project();
        newProject.setProjectName(projectDto.getProjectName());
        newProject.setDescription(projectDto.getDescription());
        newProject.setDeadline(projectDto.getDeadline());
        projectRepository.save(newProject);
    }
}