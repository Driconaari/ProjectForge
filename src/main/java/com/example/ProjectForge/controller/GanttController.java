package com.example.ProjectForge.controller;

import com.example.ProjectForge.model.GanttModel;
import com.example.ProjectForge.model.Project;
import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.repository.ProjectRepository;
import com.example.ProjectForge.repository.TaskRepository;
import com.example.ProjectForge.service.GanttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/gantt")
public class GanttController {

    @Autowired
    private TaskRepository taskRepository;

    private final GanttService ganttService;

    public GanttController(GanttService ganttService) {
        this.ganttService = ganttService;
    }

    @GetMapping("/{projectId}")
    public String getGanttModel(@PathVariable int projectId, Model model) {
        List<Task> tasks = taskRepository.getTaskByProjectID(projectId);
        List<Task> tasksWithSubtasks = new ArrayList<>();

        for (Task task : tasks) {
            Task taskWithSubtasks = taskRepository.getTaskWithSubtasks(task.getTask_id());
            tasksWithSubtasks.add(taskWithSubtasks);
        }

        model.addAttribute("ganttModels", tasksWithSubtasks);
        return "gantt";
    }

    @GetMapping("/user/{userId}")
    public String getAllGanttModelsForUser(@PathVariable int userId, Model model) {
        List<GanttModel> ganttModels = ganttService.getAllGanttModelsForUser(userId);
        model.addAttribute("ganttModels", ganttModels);
        return "gantt";
    }

    @GetMapping("/user/{userId}/{projectId}")
    public String getGanttModel(@PathVariable int userId, @PathVariable int projectId, Model model) {
        GanttModel ganttModel = ganttService.getGanttModel(userId, projectId);
        model.addAttribute("ganttModel", ganttModel);
        return "gantt";
    }
    // Add this method
   @Autowired
private ProjectRepository projectRepository;

@GetMapping("/projects")
public String getAllProjects(Model model) {
    List<Project> projects = projectRepository.getAllProjects();
    model.addAttribute("projects", projects);
    return "projects";
}


}