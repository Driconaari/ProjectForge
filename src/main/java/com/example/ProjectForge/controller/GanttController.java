package com.example.ProjectForge.controller;

import com.example.ProjectForge.model.Project;
import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.model.Subtask;
import com.example.ProjectForge.repository.IProjectRepository;
import com.example.ProjectForge.repository.SubtaskRepository;
import com.example.ProjectForge.repository.TaskRepository;
import com.example.ProjectForge.service.GanttChartService;
import com.example.ProjectForge.service.ProjectService;
import com.example.ProjectForge.service.SubtaskService;
import com.example.ProjectForge.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
@RequestMapping("/api")
public class GanttController {

    @Autowired
    private IProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GanttChartService ganttChartService;

    @Autowired
    private SubtaskRepository subtaskRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    SubtaskService subtaskService;

    @Autowired
    ProjectService projectService;

    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }


    @GetMapping("/gantt/{user_id}")
    public String showGantt(@PathVariable("user_id") int userId, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

    // Fetch the relevant projects
    List<Project> projects = projectRepository.getProjectsByID(userId);

    double totalProjectCalculatedTime = 0;
        for(
    Project project :projects)

    {
        int projectId = project.getProject_id();
        List<Task> tasks = taskRepository.getTasksWithSubtasksByProjectID(projectId);

        double projectCalculatedTime = 0;
        for (Task task : tasks) {
            double taskCalculatedTime = taskService.getProjectTimeByTaskID(task.getTask_id());
            task.setCalculatedTime(taskCalculatedTime);
            projectCalculatedTime += taskCalculatedTime;

            List<Subtask> subtasks = subtaskRepository.getSubtasksByTaskID(task.getTask_id());
            for (Subtask subtask : subtasks) {
                double subtaskHours = subtask.getHours();
                subtask.setHours(subtaskHours);
            }
            task.setSubtasks(subtasks);
        }
        project.setTasks(tasks);
        project.setProjectCalculatedTime(projectCalculatedTime);
        totalProjectCalculatedTime += projectCalculatedTime;
    }

    LocalDate projectStartDate = LocalDate.of(2024, 5, 1); // Example project start date
    List<Task> allTasks = new ArrayList<>();
        for(
    Project project :projects)

    {
        allTasks.addAll(project.getTasks());
    }

    allTasks =ganttChartService.calculateOffsetsAndDurations(allTasks,projectStartDate);

   for (Project project : projects) {
    long projectStartOffsetDays = DAYS.between(projectStartDate, project.getStart_date());
    long projectDurationDays = DAYS.between(project.getStart_date(), project.getEnd_date());
    project.setStartOffset(projectStartOffsetDays * 108); // assuming 108px per day
    project.setDuration(projectDurationDays * 108); // assuming 108px per day

    for (Task task : project.getTasks()) {
        long taskStartOffsetDays = DAYS.between(projectStartDate, task.getStart_date());
        long taskDurationDays = DAYS.between(task.getStart_date(), task.getEnd_date());
        task.setStartOffset(taskStartOffsetDays * 108); // assuming 108px per day
        task.setDuration(taskDurationDays * 108); // assuming 108px per day

        for (Subtask subtask : task.getSubtasks()) {
            long subtaskStartOffsetDays = DAYS.between(projectStartDate, subtask.getStart_date());
            long subtaskDurationDays = DAYS.between(subtask.getStart_date(), subtask.getEnd_date());
            subtask.setStartOffset(subtaskStartOffsetDays * 108); // assuming 108px per day
            subtask.setDuration(subtaskDurationDays * 108); // assuming 108px per day
        }
    }
}

        model.addAttribute("projects",projects);
        model.addAttribute("projectCalculatedTime",totalProjectCalculatedTime);
        model.addAttribute("days",

    calculateProjectDays(projectStartDate, allTasks));
        model.addAttribute("userId",userId); // Add the userId to the model so it can be used in the Thymeleaf template
        return"gantt";
}

private List<String> calculateProjectDays(LocalDate projectStartDate, List<Task> tasks) {
    LocalDate maxEndDate = projectStartDate;
    for (Task task : tasks) {
        if (task.getEnd_date() != null && task.getEnd_date().isAfter(maxEndDate)) {
            maxEndDate = task.getEnd_date();
        }
        for (Subtask subtask : task.getSubtasks()) {
            if (subtask.getEnd_date() != null && subtask.getEnd_date().isAfter(maxEndDate)) {
                maxEndDate = subtask.getEnd_date();
            }
        }
    }

    List<String> days = new ArrayList<>();
    LocalDate currentDate = projectStartDate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    while (!currentDate.isAfter(maxEndDate)) {
        days.add(currentDate.format(formatter));
        currentDate = currentDate.plusDays(1);
    }
    return days;
}
}
