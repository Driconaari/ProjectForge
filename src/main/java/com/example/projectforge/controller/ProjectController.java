package com.example.projectforge.controller;


import com.example.projectforge.model.Project;
import com.example.projectforge.model.SubProject;
import com.example.projectforge.model.Task;
import com.example.projectforge.projectRepository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
private TaskRepository taskRepository;



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
    Iterable<SubProject> subProjects = subProjectRepository.findAll();
    logger.info("Projects: {}", projects);
    logger.info("SubProjects: {}", subProjects);
    model.addAttribute("projects", projects);
    model.addAttribute("subProjects", subProjects);
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


    //tasks

  @GetMapping("/addTask")
public String showAddTaskForm(Model model) {
    model.addAttribute("task", new Task());
    model.addAttribute("projects", projectRepository.findAll());
    model.addAttribute("subProjects", subProjectRepository.findAll());
    return "addTask";
}
@PostMapping("/addTask")
public String addTask(@ModelAttribute Task task) {
    int parentProjectID = task.getParentProject().getProjectID();
    Project parentProject = projectRepository.findById(parentProjectID).orElse(null);
    if (parentProject == null) {
        throw new IllegalArgumentException("Invalid project ID:" + parentProjectID);
    }
    task.setParentProject(parentProject);
    try {
        taskRepository.save(task);
        logger.info("Task saved: {}", task);
    } catch (SQLException e) {
        logger.error("Error saving task: {}", e.getMessage());
    }
    return "redirect:/projects";
}

}
