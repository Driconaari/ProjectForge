package com.example.ProjectForge.controller;

import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.repository.IProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class GanttController {

    @Autowired
    private IProjectRepository projectRepository;

    @GetMapping("/gantt/{user_id}")
    public String showGantt(@PathVariable("user_id") int userId, Model model) {
        // Use the userId to fetch the relevant project ID
        int projectId = fetchProjectIdForUser(userId);

        List<Task> tasks = projectRepository.getTasksWithSubtasksByProjectID(projectId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("userId", userId); // Add the userId to the model so it can be used in the Thymeleaf template
        return "gantt";
    }

    private int fetchProjectIdForUser(int userId) {
        // Implement this method to fetch the project ID for the given user ID
        // For now, return a hardcoded project ID
        return 1;
    }
}