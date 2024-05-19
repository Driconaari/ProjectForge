package com.example.ProjectForge.controller;

import com.example.ProjectForge.model.Project;
import com.example.ProjectForge.model.Subtask;
import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.repository.IProjectRepository;
import com.example.ProjectForge.repository.TaskRepository;
import com.example.ProjectForge.service.GanttChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class GanttController {

    @Autowired
    private IProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GanttChartService ganttChartService;

    @GetMapping("/gantt/{user_id}")
    public String showGantt(@PathVariable("user_id") int userId, Model model) {
        // Use the userId to fetch the relevant projects
        List<Project> projects = projectRepository.getProjectsByID(userId);

        // If you want to display tasks of all projects, you can loop through the projects
        // and fetch tasks for each project using the getTasksWithSubtasksByProjectID method
        List<Task> allTasks = new ArrayList<>();
        for (Project project : projects) {
            List<Task> tasks = taskRepository.getTasksWithSubtasksByProjectID(project.getProject_id());
            for (Task task : tasks) {
                task.setProject(project); // Set the project field of the task
            }
            allTasks.addAll(tasks);
        }

        LocalDate projectStartDate = LocalDate.of(2024, 5, 1); // Example project start date
        allTasks = ganttChartService.calculateOffsetsAndDurations(allTasks, projectStartDate);

        model.addAttribute("tasks", allTasks);
        model.addAttribute("days", calculateProjectDays(projectStartDate, allTasks)); // Add time axis data
        model.addAttribute("userId", userId); // Add the userId to the model so it can be used in the Thymeleaf template
        return "gantt";
    }

    private List<String> calculateProjectDays(LocalDate projectStartDate, List<Task> tasks) {
        // Find the maximum end date from all tasks
        LocalDate maxEndDate = projectStartDate;
        for (Task task : tasks) {
            if (task.getEnd_date().isAfter(maxEndDate)) {
                maxEndDate = task.getEnd_date();
            }
            for (Subtask subtask : task.getSubtasks()) {
                if (subtask.getEnd_date().isAfter(maxEndDate)) {
                    maxEndDate = subtask.getEnd_date();
                }
            }
        }

        // Generate list of days from projectStartDate to maxEndDate
        List<String> days = new ArrayList<>();
        LocalDate currentDate = projectStartDate;
        while (!currentDate.isAfter(maxEndDate)) {
            days.add(currentDate.toString());
            currentDate = currentDate.plusDays(1);
        }
        return days;
    }
}
