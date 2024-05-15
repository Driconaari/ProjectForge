package com.example.projectforge.controller;

import com.example.projectforge.model.Project;
import com.example.projectforge.model.Task;
import com.example.projectforge.model.User;
import com.example.projectforge.service.CustomUserDetailsService;
import com.example.projectforge.service.ProjectService;
import com.example.projectforge.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {


    private final ProjectService projectService;
    private final CustomUserDetailsService customUserDetailsService;
    private final TaskService taskService;

    public ProjectController(ProjectService projectService, CustomUserDetailsService customUserDetailsService, TaskService taskService) {
        this.projectService = projectService;
        this.customUserDetailsService = customUserDetailsService;
        this.taskService = taskService;
    }

    // Utilize Spring Security to get the authenticated user
    private Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Assuming the principal object holds the user ID
            return ((User) authentication.getPrincipal()).getId();
        }
        return null; // or throw new AuthenticationException("User not authenticated");
    }
    public boolean isSignedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

  @GetMapping("/testPage")
public String showTestPage() {
    return "testPage";
}

    //Get projects from user_id
    @GetMapping
    public String showProjects(Model model) {
        Long userId = getAuthenticatedUserId();
        if (userId != null) {
            List<Project> projects = projectService.getProjectsByID(userId);
            projects.forEach(project -> {
                List<Task> tasks = taskService.getTaskByProID(project.getProject_id());
                project.calculateProjectTime(tasks);
                project.setTasks(tasks);
            });
            model.addAttribute("projects", projects);
            return "Project/projects";
        }
        return "redirect:/login"; // Redirect user to login if not authenticated
    }

   private long getUserIdFromSession(HttpSession session) {
    com.example.projectforge.model.User user = (com.example.projectforge.model.User) session.getAttribute("user");
    if (user != null) {
        return user.getUser_id();
    }
    return -1; // return -1 or throw an exception if the user is not signed in
}

  //Create project page
  @GetMapping("/create")
  public String showCreateProjectForm(Model model) {
      model.addAttribute("project", new Project());
      return "Project/createProject";
  }


    //Create project
    @PostMapping("/create")
    public String createProject(@ModelAttribute("project") Project project, RedirectAttributes redirectAttributes) {
        Long userId = getAuthenticatedUserId();
        if (userId != null) {
            projectService.createProject(project, userId);
            redirectAttributes.addFlashAttribute("message", "Project created successfully");
            return "redirect:/projects";
        }
        redirectAttributes.addFlashAttribute("error", "You must be signed in to create a project");
        return "redirect:/login";
    }

    //Edit project page
    @GetMapping(path = "/projects/{user_id}/edit/{project_id}")
    public String showEditProject(Model model, @PathVariable int project_id, @PathVariable int user_id, HttpSession session) {

        if (isSignedIn(session)) {
            Project project = projectService.getProjectByIDs(project_id, user_id);
            model.addAttribute("project", project);
            model.addAttribute("project_id", project_id);
            model.addAttribute("user_id", user_id);
            return "Project/editProject";
        }
        return "redirect:/sessionTimeout";
    }

    //Edit project
    @PostMapping(path = "/projects/{user_id}/edit/{project_id}")
    public String editProject(@PathVariable int project_id, @PathVariable int user_id, @ModelAttribute Project updatedProject) {
        Project existingProject = projectService.getProjectByIDs(project_id, user_id);

        existingProject.setProject_name(updatedProject.getProject_name());
        existingProject.setProject_description(updatedProject.getProject_description());

        // Check and update start_date if not null
        LocalDate updatedStartDate = updatedProject.getStart_date();
        if (updatedStartDate != null) {
            existingProject.setStart_date(updatedStartDate);
        }

        // Check and update end_date if not null
        LocalDate updatedEndDate = updatedProject.getEnd_date();
        if (updatedEndDate != null) {
            existingProject.setEnd_date(updatedEndDate);
        }

        projectService.editProject(existingProject, project_id, user_id);
        return "redirect:/projects/" + user_id;
    }


    //Delete project page
    @GetMapping(path = "/deleteProject/{project_id}")
    public String deleteProject(@PathVariable("project_id") int project_id, Model model, HttpSession session) {

        if (isSignedIn(session)) {
            model.addAttribute("project_id", project_id);
            Project project = projectService.getProjectByProjectID(project_id);
            model.addAttribute("project", project);
            return "Project/deleteProject";
        }
        return "redirect:/sessionTimeout";
    }

    //Delete Project
    @PostMapping(path = "/deleteProject/{project_id}")
    public String removeProject(@PathVariable("project_id") int project_id, Model model) {
        long user_id = customUserDetailsService.getUserID(project_id);
        projectService.deleteProject(project_id);
        return "redirect:/projects/" + user_id;
    }



}
