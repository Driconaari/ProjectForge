package com.example.ProjectForge.controller;

import com.example.ProjectForge.model.Project;
import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.service.ProjectService;
import com.example.ProjectForge.service.TaskService;
import com.example.ProjectForge.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final TaskService taskService;

    public ProjectController(ProjectService projectService, UserService userService, TaskService taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    @GetMapping("/{user_id}")
    public String showProjects(Model model, @PathVariable("user_id") int userId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        List<Project> projects = projectService.getProjectsByID(userId);
        for (Project project : projects) {
            List<Task> tasks = taskService.getTaskByProID(project.getProject_id());
            double projectCalculatedTime = tasks.stream()
                    .mapToDouble(task -> {
                        double taskCalculatedTime = taskService.getProjectTimeByTaskID(task.getTask_id());
                        task.setCalculatedTime(taskCalculatedTime);
                        return taskCalculatedTime;
                    }).sum();
            project.setTasks(tasks);
            project.setProjectCalculatedTime(projectCalculatedTime);
        }

        model.addAttribute("projects", projects);
        return "Project/projects";
    }

    @GetMapping("/create/{user_id}")
    public String showCreateProject(Model model, @PathVariable("user_id") int userId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        model.addAttribute("project", new Project());
        model.addAttribute("user_id", userId);
        return "Project/createProject";
    }

    @PostMapping("/create/{user_id}")
    public String createProject(@ModelAttribute("project") Project project, @PathVariable("user_id") int userId) {
        projectService.createProject(project, userId);
        return "redirect:/projects/" + userId;
    }

    @GetMapping("/{user_id}/edit/{project_id}")
    public String showEditProject(Model model, @PathVariable("project_id") int projectId, @PathVariable("user_id") int userId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        Project project = projectService.getProjectByIDs(projectId, userId);
        model.addAttribute("project", project);
        model.addAttribute("project_id", projectId);
        model.addAttribute("user_id", userId);
        return "Project/editProject";
    }

    @PostMapping("/{user_id}/edit/{project_id}")
    public String editProject(@PathVariable("project_id") int projectId, @PathVariable("user_id") int userId, @ModelAttribute("project") Project updatedProject) {
        Project existingProject = projectService.getProjectByIDs(projectId, userId);

        existingProject.setProject_name(updatedProject.getProject_name());
        existingProject.setProject_description(updatedProject.getProject_description());

        if (updatedProject.getStart_date() != null) {
            existingProject.setStart_date(updatedProject.getStart_date());
        }

        if (updatedProject.getEnd_date() != null) {
            existingProject.setEnd_date(updatedProject.getEnd_date());
        }

        projectService.editProject(existingProject, projectId, userId);
        return "redirect:/projects/" + userId;
    }

    @GetMapping("/delete/{project_id}")
    public String showDeleteProject(@PathVariable("project_id") int projectId, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        Project project = projectService.getProjectByProjectID(projectId);
        model.addAttribute("project", project);
        return "Project/deleteProject";
    }

    @PostMapping("/delete/{project_id}")
    public String deleteProject(@PathVariable("project_id") int projectId, HttpSession session) {
        int userId = (int) session.getAttribute("user_id");
        projectService.deleteProject(projectId);
        return "redirect:/projects/" + userId;
    }
}
