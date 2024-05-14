package com.example.projectforge.service;

import com.example.projectforge.projectRepository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.projectforge.dto.ProjectDto;


    @Service
    public class ProjectService {

        @Autowired
        private ProjectRepository ProjectRepository;

        public void createProject(ProjectDto Projectdto) {
            com.example.projectforge.model.Project newProject = new com.example.projectforge.model.Project();
            newProject.setProjectName(Projectdto.getProjectName());
            newProject.setDescription(Projectdto.getDescription());
            newProject.setDeadline(Projectdto.getDeadline());
            // Any other properties you need to set for Project

            // Save the new Project to the database
            ProjectRepository.save(newProject);
        }
    }

