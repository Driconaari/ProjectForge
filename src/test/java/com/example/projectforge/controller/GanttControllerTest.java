package com.example.projectforge.controller;

import com.example.ProjectForge.controller.GanttController;
import com.example.ProjectForge.model.Project;
import com.example.ProjectForge.repository.IProjectRepository;
import com.example.ProjectForge.repository.ITaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/*
@ExtendWith(MockitoExtension.class)
public class GanttControllerTest {

    @Mock
private ITaskRepository taskRepository;

    @Mock
    private IProjectRepository projectRepository;

    @InjectMocks
    private GanttController ganttController;

    private MockMvc mockMvc;

   @BeforeEach
public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(ganttController).build();
    when(taskRepository.getTasksWithSubtasksByProjectID(anyInt())).thenReturn(new ArrayList<>());
}

    @Test
    public void testShowGantt() throws Exception {
        //Test data
        int userId = 1;
        Project project1 = new Project();
        Project project2 = new Project();
        List<Project> expectedProjects = Arrays.asList(project1, project2);

        //Get tasks with subtasks by project ID method
        when(taskRepository.getTasksWithSubtasksByProjectID(anyInt())).thenReturn(new ArrayList<>());
        //Show gantt method
        when(projectRepository.getProjectsByID(userId)).thenReturn(expectedProjects);

        //Verify the projects are retrieved
        mockMvc.perform(get("/api/gantt/{user_id}", userId))
                .andExpect(status().isOk())
                .andExpect(view().name("gantt"));
    }
}

 */