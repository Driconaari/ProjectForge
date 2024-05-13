package com.example.projectforge.service;

import com.example.projectforge.model.SubProject;
import com.example.projectforge.projectRepository.SubProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubProjectService {

    private final SubProjectRepository subProjectRepository;

    @Autowired
    public SubProjectService(SubProjectRepository subProjectRepository) {
        this.subProjectRepository = subProjectRepository;
    }

    public List<SubProject> getAllSubProjects() {
        return subProjectRepository.findAll();
    }
}