package com.example.projectforge.projectRepository;

import com.example.projectforge.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findByName(String projectName);
}