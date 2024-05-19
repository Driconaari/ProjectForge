package com.example.ProjectForge.controller;

import com.example.ProjectForge.model.Project;
import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.repository.IProjectRepository;
import com.example.ProjectForge.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GanttController {

    @Autowired
    private IProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/gantt/{user_id}")
    public String showGantt(@PathVariable("user_id") int userId, Model model) {
        // Use the userId to fetch the relevant projects
        List<Project> projects = projectRepository.getProjectsByID(userId);

        // If you want to display tasks of all projects, you can loop through the projects
        // and fetch tasks for each project using the getTasksWithSubtasksByProjectID method
        List<Task> allTasks = new ArrayList<>();
        for (Project project : projects) {
            List<Task> tasks = taskRepository.getTasksWithSubtasksByProjectID(project.getProject_id());
            allTasks.addAll(tasks);
        }

        model.addAttribute("tasks", allTasks);
        model.addAttribute("userId", userId); // Add the userId to the model so it can be used in the Thymeleaf template
        return "gantt";
    }
}