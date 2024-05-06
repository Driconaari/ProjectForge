package com.example.projectforge.repository;

import com.example.projectforge.model.Project;
import java.util.List;

public interface ProjectRepo {
    Project create(Project project);
    Project save(Project project);
    Project findById(Long id);
    List<Project> findAll();
    void delete(Project project);
}