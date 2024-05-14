package com.example.projectforge.controller;

import com.example.projectforge.dto.ProjectDto;
import com.example.projectforge.dto.SubProjectDto;
import com.example.projectforge.dto.TaskDto;
import com.example.projectforge.dto.SubTaskDto;
import com.example.projectforge.service.ProjectService;
import com.example.projectforge.service.SubProjectService;
import com.example.projectforge.service.TaskService;
import com.example.projectforge.service.SubTaskService;
import com.example.projectforge.projectRepository.ProjectRepository;
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

    @Autowired
    private SubProjectService subProjectService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SubTaskService subTaskService;

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
    public String showProjectMenuPage() {
        return "projectMenu";
    }

    @GetMapping("/createProject")
    public String showCreateProjectPage(Model model) {
        model.addAttribute("project", new ProjectDto());
        return "createProject";
    }

    @PostMapping("/createProject")
    public String createProject(@ModelAttribute("project") ProjectDto projectDto) {
        projectService.createProject(projectDto);
        return "redirect:/";
    }

    @GetMapping("/createSubProject")
    public String showCreateSubProjectPage(Model model) {
        model.addAttribute("subProject", new SubProjectDto());
        return "createSubProject";
    }

    @PostMapping("/createSubProject")
    public String createSubProject(@ModelAttribute("subProject") SubProjectDto subProjectDto) {
        subProjectService.createSubProject(subProjectDto);
        return "redirect:/";
    }

    @GetMapping("/createTask")
    public String showCreateTaskPage(Model model) {
        model.addAttribute("task", new TaskDto());
        return "createTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute("task") TaskDto taskDto) {
        taskService.createTask(taskDto);
        return "redirect:/";
    }


    @GetMapping("/createSubTask")
    public String showCreateSubTaskPage(Model model) {
        model.addAttribute("subTask", new SubTaskDto());
        return "createSubTask";
    }

    @PostMapping("/createSubTask")
    public String createSubTask(@ModelAttribute("subTask") SubTaskDto subTaskDto) {
        subTaskService.createSubTask(subTaskDto);
        return "redirect:/";
    }
}
