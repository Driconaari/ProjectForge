package com.example.projectforge.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.projectforge.projectRepository.ProjectRepository;


@Controller
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;


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
    public String showCreateProjectPage() {
        return "createProject"; // Return the name of the HTML file (without the extension)
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
