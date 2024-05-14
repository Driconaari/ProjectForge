package com.example.projectforge.service;

import com.example.projectforge.dto.SubProjectDto;
import com.example.projectforge.model.SubProject;
import com.example.projectforge.projectRepository.SubProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubProjectService {
    @Autowired
    private SubProjectRepository subProjectRepository;

    public void createSubProject(SubProjectDto subProjectDto) {
        SubProject newSubProject = new SubProject();
        newSubProject.setSubProjectName(subProjectDto.getSubProjectName());
        newSubProject.setDescription(subProjectDto.getDescription());
        newSubProject.setDeadline(subProjectDto.getDeadline());
        subProjectRepository.save(newSubProject);
    }
}
