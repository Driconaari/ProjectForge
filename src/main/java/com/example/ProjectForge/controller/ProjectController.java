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
@RequestMapping("")
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

    @GetMapping("projects/{user_id}")
    public String showProjects(Model model, @PathVariable("user_id") int userId, HttpSession session) {
        if (isLoggedIn(session)) {
            List<Project> projects = projectService.getProjectsByID(userId);
            for (Project project : projects) {
                int projectId = project.getProject_id();
                List<Task> tasks = taskService.getTaskByProID(projectId);

                double projectCalculatedTime = 0;
                for (Task task : tasks) {
                    double taskCalculatedTime = taskService.getProjectTimeByTaskID(task.getTask_id());
                    task.setCalculatedTime(taskCalculatedTime);
                    projectCalculatedTime += taskCalculatedTime;
                }
                project.setTasks(tasks);
                project.setProjectCalculatedTime(projectCalculatedTime);
            }
            model.addAttribute("projects", projects);
            return "Project/projects";
        }
        return "redirect:/sessionTimeout";
    }

    @GetMapping("projects/create/{user_id}")
    public String showCreateProject(Model model, @PathVariable("user_id") int userId, HttpSession session) {
        if (isLoggedIn(session)) {
            Project project = new Project();
            model.addAttribute("project", project);
            model.addAttribute("user_id", userId);
            return "Project/createProject";
        }
        return "redirect:/sessionTimeout";
    }

    @PostMapping("projects/create/{user_id}")
    public String createProject(@ModelAttribute("project") Project project, @PathVariable("user_id") int userId) {
        projectService.createProject(project, userId);
        return "redirect:/projects/" + userId;
    }

    @GetMapping("/projects/{user_id}/edit/{project_id}")
    public String showEditProject(Model model, @PathVariable("project_id") int projectId, @PathVariable("user_id") int userId, HttpSession session) {
        if (isLoggedIn(session)) {
            Project project = projectService.getProjectByIDs(projectId, userId);
            model.addAttribute("project", project);
            model.addAttribute("project_id", projectId);
            model.addAttribute("user_id", userId);
            return "Project/editProject";
        }
        return "redirect:/sessionTimeout";
    }

    @PostMapping("/projects/{user_id}/edit/{project_id}")
    public String editProject(@PathVariable("project_id") int projectId, @PathVariable("user_id") int userId, @ModelAttribute Project updatedProject) {
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

    @GetMapping("/deleteProject/{project_id}")
    public String deleteProject(@PathVariable("project_id") int projectId, Model model, HttpSession session) {
        if (isLoggedIn(session)) {
            Project project = projectService.getProjectByProjectID(projectId);
            model.addAttribute("project_id", projectId);
            model.addAttribute("project", project);
            return "Project/deleteProject";
        }
        return "redirect:/sessionTimeout";
    }

    @PostMapping("/deleteProject/{project_id}")
    public String removeProject(@PathVariable("project_id") int projectId) {
        int userId = userService.getUserID(projectId);
        projectService.deleteProject(projectId);
        return "redirect:/projects/" + userId;
    }
}
