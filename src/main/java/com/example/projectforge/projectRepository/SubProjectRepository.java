package com.example.projectforge.projectRepository;

import com.example.projectforge.model.SubProject;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public interface SubProjectRepository {
    void addSubProject(SubProject subProject) throws SQLException;
    SubProject findBySubProjectName(String subProjectName) throws SQLException;
    SubProject getSubProjectById(int subProjectId) throws SQLException;
    // Add other methods as needed
}