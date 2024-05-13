package com.example.projectforge.controller;

import com.example.projectforge.model.Project;
import com.example.projectforge.model.SubProject;
import com.example.projectforge.projectRepository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.projectforge.projectRepository.SubProjectRepository;

import java.sql.SQLException;

@Controller
public class SubProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SubProjectRepository subProjectRepository;


    //logger to log the data
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);


    //Show subprojects
    @GetMapping("/subprojects")
    public String showSubProjects(Model model) {
        Iterable<SubProject> subProjects = subProjectRepository.findAll();
        System.out.println(subProjects); // Add this line
        logger.info("SubProjects: {}", subProjects);
        model.addAttribute("subProjects", subProjects);
        return "subprojects";
    }


    //subprojects
    @GetMapping("/addSubProject")
    public String showAddSubProjectForm(Model model) {
        model.addAttribute("subproject", new SubProject());
        model.addAttribute("projects", projectRepository.findAll());
        return "addSubProject";
    }

    //added sqlexception for subproject too
    @PostMapping("/addSubProject")
    public String addSubProject(@ModelAttribute SubProject subproject) throws SQLException {
        int parentProjectID = subproject.getParentProject().getProjectID();
        Project parentProject = projectRepository.findById(parentProjectID).orElse(null);
        if (parentProject == null) {
            throw new IllegalArgumentException("Invalid project ID:" + parentProjectID);
        }
        subproject.setParentProject(parentProject);
        subProjectRepository.save(subproject);
        logger.info("SubProject saved: {}", subproject);
        return "redirect:/projects";
    }


}
