package com.example.projectforge.controller;

import com.example.projectforge.dto.ProjectDto;
import com.example.projectforge.service.ProjectService;
import com.example.projectforge.projectRepository.ProjectRepository; // Add this import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "index";
    }

    @GetMapping("/projects")
    public String projects() {
        return "projects";
    }

    @GetMapping("/projectMenu")
    public String showprojectMenuPage() {
        return "projectMenu"; // Return the name of the HTML file (without the extension)
    }

    @GetMapping("/createProject")
    public String showCreateProjectPage(Model model) {
        model.addAttribute("project", new ProjectDto());
        return "createProject"; // Return the name of the HTML file (without the extension)
    }

    @PostMapping("/createProject")
    public String createProject(@ModelAttribute("project") ProjectDto projectDto) {
        // Ensure projectService is not null
        if(projectService != null) {
            projectService.createProject(projectDto);
            return "redirect:/"; // Redirect to the home page or any other appropriate page
        } else {
            // Handle the case when projectService is null
            return "error"; // Redirect to an error page
        }
    }

    @GetMapping("/createSubProject")
    public String showCreateSubProjectPage() {
        return "createSubProject"; // Return the name of the HTML file (without the extension)
    }

    @GetMapping("/createTask")
    public String showCreateTaskPage() {
        return "createTask"; // Return the name of the HTML file (without the extension)
    }

    @GetMapping("/createSubTask")
    public String showCreateSubTaskPage() {
        return "createSubTask"; // Return the name of the HTML file (without the extension)
    }
}
