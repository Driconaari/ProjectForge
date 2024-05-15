package com.example.projectforge.controller;

import com.example.projectforge.model.Project;
import com.example.projectforge.model.Task;
import com.example.projectforge.service.CustomUserDetailsService;
import com.example.projectforge.service.ProjectService;
import com.example.projectforge.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

//@RequestMapping(path = "")
@org.springframework.stereotype.Controller
public class ProjectController {

    private final ProjectService projectService;
    private final CustomUserDetailsService customUserDetailsService;
    private final TaskService taskService;

    public ProjectController(ProjectService projectService, CustomUserDetailsService customUserDetailsService, TaskService taskService) {
        this.projectService = projectService;
        this.customUserDetailsService = customUserDetailsService;
        this.taskService = taskService;
    }

    public boolean isSignedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

  @GetMapping("/testPage")
public String showTestPage() {
    return "testPage";
}

    //Get projects from user_id
    @GetMapping(path = "projects")
    public String showProjects(Model model, HttpSession session) {
        long user_id = getUserIdFromSession(session);
        if (user_id != -1) {
            List<Project> projects = projectService.getProjectsByID(user_id);

            for (Project project : projects) {
                int project_id = project.getProject_id();
                List<Task> tasks = taskService.getTaskByProID(project_id);

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

   private long getUserIdFromSession(HttpSession session) {
    com.example.projectforge.model.User user = (com.example.projectforge.model.User) session.getAttribute("user");
    if (user != null) {
        return user.getUser_id();
    }
    return -1; // return -1 or throw an exception if the user is not signed in
}

  //Create project page
@GetMapping("/projects/create")
public String showCreateProjectForm(Model model) {
    model.addAttribute("project", new Project());
    return "Project/createProject";
}


    //Create project
@PostMapping(path = "/projects/create")
public String createProject(@ModelAttribute("project") Project project, HttpSession session) {
    // Get the user_id of the currently logged-in user
    long signedInUserId = getUserIdFromSession(session);

    // If the user is signed in, proceed with the project creation
    if (signedInUserId != -1) {
        com.example.projectforge.model.User currentUser = customUserDetailsService.getUserById(signedInUserId);
        if (currentUser != null) {
            project.setUser(currentUser);
            Project createdProject = projectService.createProject(project, signedInUserId);
            if (createdProject != null) {
                return "redirect:/projects";
            }
        }
    }
    // If the user is not signed in or project creation failed, redirect to the login page
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
