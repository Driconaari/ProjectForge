package com.example.projectforge.controller;


import com.example.projectforge.model.Project;
import com.example.projectforge.model.SubProject;
import com.example.projectforge.projectRepository.ProjectDAO;
import com.example.projectforge.projectRepository.SubProjectDAO;
import com.example.projectforge.projectRepository.SubProjectRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.projectforge.projectRepository.ProjectRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;


@Controller
public class ProjectController {

    //logger to log the data
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private SubProjectDAO subProjectDAO;


    //showing the data with sting from the model class on the index, both projects and subprojecs
    // showing the projectslist in the projects.html
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("projects", projectRepository.findAll());

        //showing the subprojects in the index.html
        List<SubProject> subProjects = subProjectRepository.findAll();
        model.addAttribute("subProjects", subProjects);

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

    //added sqlexception which is reallu nice to locate wrong DAO
    @PostMapping("/addProject")
    @Transactional
    public String addProject(@ModelAttribute Project project) throws SQLException {
        logger.info("Received project: {}", project);
        projectRepository.saveProject(project);
        logger.info("Project saved: {}", project);
        return "redirect:/projects";
    }

    //subprojects
    @GetMapping("/addSubProject")
    public String showCreateSubProjectPage(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "addSubProject"; // Return the name of the HTML file (without the extension)
    }

//added sqlexception for subproject too
 @PostMapping("/addSubProject")
public String addSubProject(@ModelAttribute SubProject subproject, @RequestParam("parentProjectID") int parentProjectID) throws SQLException {
    logger.info("Received subproject: {}", subproject);
    Project parentProject = projectRepository.findById(parentProjectID).orElse(null);
    if (parentProject == null) {
        throw new IllegalArgumentException("Invalid project ID:" + parentProjectID);
    }
    subproject.setParentProject(parentProject);
    subProjectRepository.save(subproject);
    logger.info("SubProject saved: {}", subproject);
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
