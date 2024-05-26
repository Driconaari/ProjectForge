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

@RequestMapping(path = "")
@Controller
public class ProjectController {

    private ProjectService projectService;
    private UserService userService;
    private TaskService taskService;

    public ProjectController(ProjectService projectService, UserService userService, TaskService taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }

    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    //Get projects from user_id
    @GetMapping(path = "projects/{user_id}")
    public String showProjects(Model model, @PathVariable int user_id, HttpSession session) {

       if(isLoggedIn(session)) {

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



    //Create project page
    @GetMapping(path = "projects/create/{user_id}")
    public String showCreateProject(Model model, @PathVariable int user_id, HttpSession session) {

        if(isLoggedIn(session)) {
            Project project = new Project();
            model.addAttribute("project", project);
            model.addAttribute("user_id", user_id);
            return "Project/createProject";
        }
        return "redirect:/sessionTimeout";
    }

    //Create project
    @PostMapping(path = "projects/create/{user_id}")            //localhost//8080:/projects/create/1
    public String createProject(@ModelAttribute("project") Project project, @PathVariable int user_id) {
        projectService.createProject(project, user_id);
        return "redirect:/projects/" + user_id;
    }



    //Edit project page
    @GetMapping(path = "/projects/{user_id}/edit/{project_id}")
    public String showEditProject(Model model, @PathVariable int project_id, @PathVariable int user_id, HttpSession session) {

        if (isLoggedIn(session)) {
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

        if (isLoggedIn(session))  {
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
        int user_id = userService.getUserID(project_id);
        projectService.deleteProject(project_id);
        return "redirect:/projects/" + user_id;
    }
}
