package com.example.projectforge.controller;


import com.example.projectforge.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.projectforge.projectRepository.ProjectRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;


    //showing the data with sting from the model class
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "index";
    }

    @GetMapping("/projects")
    public String projects() {
        return "projects";
    }

    @GetMapping("/addProject")
    public String addProject() {
        return "addProject";
    }

    @PostMapping("/addProject")
    public String addProject(@ModelAttribute Project project) {
        projectRepository.save(project);
        return "redirect:/projects";
    }


}
