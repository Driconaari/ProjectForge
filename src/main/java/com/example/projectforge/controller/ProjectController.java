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
    public String showProjects(Model model) {
        Iterable<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            logger.info("Project: {}", project);
            logger.info("SubProjects: {}", project.getSubprojects());
        }
        model.addAttribute("projects", projects);
        return "projects";
    }

    //create project page
    @GetMapping("/createProject")
    public String showCreateProjectPage() {
        return "createProject";
    }


    //projects
    @GetMapping("/addProject")
    public String addProject() {
        return "addProject";
    }

    //added sqlexception which is really nice to locate wrong DAO
    @PostMapping("/addProject")
    @Transactional
    public String addProject(@ModelAttribute Project project) throws SQLException {
        logger.info("Received project: {}", project);
        projectRepository.saveProject(project);
        logger.info("Project saved: {}", project);
        return "redirect:/projects";
    }



}
