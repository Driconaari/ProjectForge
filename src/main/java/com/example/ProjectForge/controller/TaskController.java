package com.example.ProjectForge.controller;

import com.example.ProjectForge.dto.TaskSubtaskDTO;
import com.example.ProjectForge.model.Project;
import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.repository.ProjectRepository;
import com.example.ProjectForge.service.ProjectService;
import com.example.ProjectForge.service.TaskService;
import com.example.ProjectForge.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectRepository projectRepository;

    public TaskController(TaskService taskService, UserService userService, ProjectService projectService, ProjectRepository projectRepository) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    @GetMapping("/{project_id}")
    public String showTasks(Model model, @PathVariable("project_id") int projectId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        List<Task> tasks = taskService.getTaskByProID(projectId);
        int userId = userService.getUserID(projectId);
        double projectCalculatedTime = projectService.getProjectTimeByProjectID(projectId);

        for (Task task : tasks) {
            double taskCalculatedTime = taskService.getProjectTimeByTaskID(task.getTask_id());
            task.setCalculatedTime(taskCalculatedTime);

            Optional<Project> projectOptional = projectRepository.findById(task.getProject_id());
            projectOptional.ifPresent(task::setProject);
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("user_id", userId);
        model.addAttribute("projectCalculatedTime", projectCalculatedTime);
        return "Task/tasks";
    }

    @GetMapping("/create/{project_id}")
    public String showCreateTask(Model model, @PathVariable("project_id") int projectId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        model.addAttribute("task", new Task());
        model.addAttribute("project_id", projectId);
        return "Task/createTask";
    }

    @PostMapping("/create/{project_id}")
    public String createTask(@ModelAttribute("task") Task task, @PathVariable("project_id") int projectId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        taskService.createTask(task, projectId);
        return "redirect:/tasks/" + projectId;
    }

    @GetMapping("/{project_id}/edit/{task_id}")
    public String showEditTask(Model model, @PathVariable("task_id") int taskId, @PathVariable("project_id") int projectId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        Task task = taskService.getTaskByIDs(taskId, projectId);
        Project project = projectService.getProjectByProjectID(projectId);

        model.addAttribute("task", task);
        model.addAttribute("task_id", taskId);
        model.addAttribute("project_id", projectId);
        model.addAttribute("project_start_date", project.getStart_date());
        model.addAttribute("project_end_date", project.getEnd_date());
        return "Task/editTask";
    }

    @PostMapping("/{project_id}/edit/{task_id}")
    public String editTask(@PathVariable("task_id") int taskId, @PathVariable("project_id") int projectId, @ModelAttribute("task") Task updatedTask, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        Task task = taskService.getTaskByIDs(taskId, projectId);

        task.setTask_name(updatedTask.getTask_name());
        task.setHours(updatedTask.getHours());
        task.setStatus(updatedTask.getStatus());

        if (updatedTask.getStart_date() != null) {
            task.setStart_date(updatedTask.getStart_date());
        }

        if (updatedTask.getEnd_date() != null) {
            task.setEnd_date(updatedTask.getEnd_date());
        }

        taskService.editTask(task, taskId, projectId);
        return "redirect:/tasks/" + projectId;
    }

    @GetMapping("/delete/{task_id}")
    public String showDeleteTask(@PathVariable("task_id") int taskId, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        Task task = taskService.getTaskById(taskId);

        model.addAttribute("task_id", taskId);
        model.addAttribute("task", task);
        return "Task/deleteTask";
    }

    @PostMapping("/delete/{task_id}")
    public String deleteTask(@PathVariable("task_id") int taskId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        int projectId = taskService.getProjectIDbyTaskID(taskId);
        taskService.deleteTask(taskId);
        return "redirect:/tasks/" + projectId;
    }

    @GetMapping("/project/{project_id}")
    public String showProject(Model model, @PathVariable("project_id") int projectId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        List<TaskSubtaskDTO> taskSubtasks = taskService.getTaskSubtasksByProID(projectId);
        double projectCalculatedTime = projectService.getProjectTimeByProjectID(projectId);
        int userId = userService.getUserID(projectId);

        for (TaskSubtaskDTO task : taskSubtasks) {
            double taskCalculatedTime = taskService.getProjectTimeByTaskID(task.getId());
            task.setCalculatedTime(taskCalculatedTime);
        }

        model.addAttribute("taskSubtask", taskSubtasks);
        model.addAttribute("projectCalculatedTime", projectCalculatedTime);
        model.addAttribute("user_id", userId);
        return "Task/taskSubtask";
    }
}
