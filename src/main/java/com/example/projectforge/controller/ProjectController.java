package com.example.projectforge.controller;


import com.example.projectforge.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.projectforge.projectRepository.ProjectRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;


    //showing the data with sting from the model class on the index,     //showing the projectslist in the projects.html
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "index";
    }

    @GetMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "projects";
    }

    //create project page
    @GetMapping("/createProject")
    public String showCreateProjectPage() {
        return "createProject"; // Return the name of the HTML file (without the extension)
    }


    //projects
    @GetMapping("/addProject")
    public String addProject() {
        return "addProject";
    }

    @PostMapping("/addProject")
    public String addProject(@ModelAttribute Project project) {
        projectRepository.save(project);
        return "redirect:/projects";
    }

    //subprojects
    @GetMapping("/addSubProject")
    public String showCreateSubProjectPage(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "addSubProject"; // Return the name of the HTML file (without the extension)
    }

    @PostMapping("/addSubProject")
    public String addSubProject(@ModelAttribute Project subproject, @RequestParam("parentProjectID") int parentProjectID) {
        Project parentProject = projectRepository.findById(parentProjectID).orElse(null);
        if (parentProject == null) {
            throw new IllegalArgumentException("Invalid project ID:" + parentProjectID);
        }
        subproject.setParentProject(parentProject);
        projectRepository.save(subproject);
        return "redirect:/projects";
    }


    //tasks

    @GetMapping("/addTask")
    public String showCreateTaskPage() {
        return "addTask"; // Return the name of the HTML file (without the extension)
    }

    @GetMapping("/addSubTask")
    public String showCreateSubTaskPage() {
        return "addSubTask"; // Return the name of the HTML file (without the extension)
    }

}
