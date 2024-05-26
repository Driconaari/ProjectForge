package com.example.projectforge.repository;

import com.example.ProjectForge.model.Project;
import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ComponentScan("com.example.ProjectForge.repository.ProjectRepository")
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void testGetAllProjects() {
        List<Project> projects = projectRepository.getAllProjects();

        // Verify the method returns a list
        assertNotNull(projects);

        // Add more assertions based on your test data and requirements
    }

    @Test
    public void testGetTasksWithSubtasksByProjectID() {
        int projectId = 10; // replace with your test data
        List<Task> tasks = projectRepository.getTasksWithSubtasksByProjectID(projectId);

        // Verify the method returns a list
        assertNotNull(tasks);

        // Add more assertions based on your test data and requirements
    }

    @Test
    public void testFindById() {
        int projectId = 10; // replace with your test data
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        // Verify the method returns an Optional
        assertNotNull(projectOptional);

        // Verify the Optional is not empty
        assertTrue(projectOptional.isPresent());

        // Verify the project has the expected ID
        assertEquals(projectId, projectOptional.get().getProject_id());

        // Add more assertions based on your test data and requirements
    }
}