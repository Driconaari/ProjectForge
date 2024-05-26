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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
@RequestMapping("/api")
public class GanttController {

    private final IProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final GanttChartService ganttChartService;
    private final SubtaskRepository subtaskRepository;
    private final TaskService taskService;
    private final SubtaskService subtaskService;
    private final ProjectService projectService;
    private static final Logger logger = LoggerFactory.getLogger(GanttController.class);

    public GanttController(IProjectRepository projectRepository, TaskRepository taskRepository,
                           GanttChartService ganttChartService, SubtaskRepository subtaskRepository,
                           TaskService taskService, SubtaskService subtaskService, ProjectService projectService) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.ganttChartService = ganttChartService;
        this.subtaskRepository = subtaskRepository;
        this.taskService = taskService;
        this.subtaskService = subtaskService;
        this.projectService = projectService;
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    @GetMapping("/gantt/{user_id}")
    public String showGantt(@PathVariable("user_id") int userId, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        try {
            List<Project> projects = projectRepository.getProjectsByID(userId);
            double totalProjectCalculatedTime = calculateProjectTimes(projects);
            LocalDate projectStartDate = LocalDate.of(2024, 5, 1); // Example project start date
            List<Task> allTasks = getAllTasks(projects);
            allTasks = ganttChartService.calculateOffsetsAndDurations(allTasks, projectStartDate);

            calculateProjectOffsetsAndDurations(projects, projectStartDate);

            model.addAttribute("projects", projects);
            model.addAttribute("projectCalculatedTime", totalProjectCalculatedTime);
            model.addAttribute("days", calculateProjectDays(projectStartDate, allTasks));
            model.addAttribute("userId", userId);

            return "gantt";
        } catch (Exception e) {
            logger.error("Error generating Gantt chart", e);
            model.addAttribute("errorMessage", "An error occurred while generating the Gantt chart. Please try again.");
            return "error";
        }
    }

    private double calculateProjectTimes(List<Project> projects) {
        double totalProjectCalculatedTime = 0;
        for (Project project : projects) {
            double projectCalculatedTime = 0;
            List<Task> tasks = taskRepository.getTasksWithSubtasksByProjectID(project.getProject_id());

            for (Task task : tasks) {
                double taskCalculatedTime = taskService.getProjectTimeByTaskID(task.getTask_id());
                task.setCalculatedTime(taskCalculatedTime);
                projectCalculatedTime += taskCalculatedTime;

                List<Subtask> subtasks = subtaskRepository.getSubtasksByTaskID(task.getTask_id());
                for (Subtask subtask : subtasks) {
                    subtask.setHours(subtask.getHours());
                }
                task.setSubtasks(subtasks);
            }
            project.setTasks(tasks);
            project.setProjectCalculatedTime(projectCalculatedTime);
            totalProjectCalculatedTime += projectCalculatedTime;
        }
        return totalProjectCalculatedTime;
    }

    private List<Task> getAllTasks(List<Project> projects) {
        List<Task> allTasks = new ArrayList<>();
        for (Project project : projects) {
            allTasks.addAll(project.getTasks());
        }
        return allTasks;
    }

    private void calculateProjectOffsetsAndDurations(List<Project> projects, LocalDate projectStartDate) {
        for (Project project : projects) {
            long projectStartOffsetDays = DAYS.between(projectStartDate, project.getStart_date());
            long projectDurationDays = DAYS.between(project.getStart_date(), project.getEnd_date());
            project.setStartOffset(projectStartOffsetDays * 10); // assuming 10px per day
            project.setDuration(projectDurationDays * 10); // assuming 10px per day
        }
    }

    private List<String> calculateProjectDays(LocalDate projectStartDate, List<Task> tasks) {
        LocalDate maxEndDate = tasks.stream()
                .flatMap(task -> {
                    List<LocalDate> dates = new ArrayList<>();
                    dates.add(task.getEnd_date());
                    dates.addAll(task.getSubtasks().stream()
                            .map(Subtask::getEnd_date)
                            .toList());
                    return dates.stream();
                })
                .filter(date -> date != null && date.isAfter(projectStartDate))
                .max(LocalDate::compareTo)
                .orElse(projectStartDate);

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
