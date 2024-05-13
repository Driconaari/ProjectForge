package com.example.projectforge.projectRepository;

import com.example.projectforge.model.Project;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
public interface ProjectRepository {
    void saveProject(Project project) throws SQLException;
    Project findByProjectName(String projectName) throws SQLException;
    Project getProjectById(int projectId) throws SQLException;

    Optional<Project> findById(int parentProjectID);

    void save(Project subproject);

    Iterable<Project> findAll();
}