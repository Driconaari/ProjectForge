package com.example.ProjectForge.service;

import com.example.ProjectForge.model.GanttModel;
import com.example.ProjectForge.model.Project;
import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.model.User;
import com.example.ProjectForge.repository.ProjectRepository;
import com.example.ProjectForge.repository.TaskRepository;
import com.example.ProjectForge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GanttService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public GanttService(UserRepository userRepository, ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    // Assume these repositories are injected via constructor

    public GanttModel getGanttModel(int userId, int projectId) {
        GanttModel ganttModel = new GanttModel();

        User user = userRepository.getUserFromId(userId);
        Project project = projectRepository.getProjectByProjectID(projectId);
        List<Task> tasks = taskRepository.getTaskByProjectID(projectId);

        ganttModel.setUser(user);
        ganttModel.setProject(project);
        ganttModel.setTasks(tasks);

        return ganttModel;
    }
}