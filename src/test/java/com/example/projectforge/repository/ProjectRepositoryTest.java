package com.example.projectforge.repository;

/*
import com.example.ProjectForge.model.Project;
import com.example.ProjectForge.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ComponentScan("com.example.ProjectForge.repository.ProjectRepository")
public class ProjectRepositoryTest {

    @Mock
    private ProjectRepository projectRepository;

//    @Autowired
    //private ProjectRepository projectRepository;

    @Test
    public void testCreateProject() {
        //Test data
        int user_id = 999999; // High number unlikely to exist
        Project project = new Project(999999, "Test Project", "Project description", LocalDate.now(), LocalDate.now(), user_id);

        //Create project method
        projectRepository.createProject(project, user_id);

        //Verify the project is created
        assertNotNull(project.getProject_id());
        assertEquals("Test Project", project.getProject_name());
        assertEquals("Project description", project.getProject_description());
    }

    @Test
    public void testDeleteProject() {
        //Test data
        int userId = 999999; // High number unlikely to exist
        Project project = new Project(999999, "Test Project", "Project description", LocalDate.now(), LocalDate.now(), userId);

        //Create project method
        projectRepository.createProject(project, userId);

        //Delete project method
        projectRepository.deleteProject(project.getProject_id());

        //Verify project is deleted
        assertNull(projectRepository.findById(project.getProject_id()).orElse(null));
    }

    @Test
    public void testEditProject() {
        //Test data
        int userId = 999999; // High number unlikely to exist
        Project project = new Project(999999, "Test Project", "Project description", LocalDate.now(), LocalDate.now(), userId);

        //Edit project method
        projectRepository.createProject(project, userId);

        //Edit project values
        project.setProject_name("Updated Project");
        project.setProject_description("Updated description");
        project.setStart_date(LocalDate.now().plusDays(1));
        project.setEnd_date(LocalDate.now().plusDays(2));

        //Edit project method
        projectRepository.editProject(project, project.getProject_id(), userId);

        // Define behavior for findById()
        when(projectRepository.findById(project.getProject_id())).thenReturn(Optional.of(project));

        //Get updated project
        Project updatedProject = projectRepository.findById(project.getProject_id()).orElse(null);

        //Verify project is updated
        assertNotNull(updatedProject);
        assertEquals(project.getProject_name(), updatedProject.getProject_name());
        assertEquals(project.getProject_description(), updatedProject.getProject_description());
        assertEquals(project.getStart_date(), updatedProject.getStart_date());
        assertEquals(project.getEnd_date(), updatedProject.getEnd_date());
    }
}

 */