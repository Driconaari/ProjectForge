package com.example.ProjectForge.controller;

import com.example.ProjectForge.model.Subtask;
import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.service.ProjectService;
import com.example.ProjectForge.service.SubtaskService;
import com.example.ProjectForge.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/subtasks")
public class SubtaskController {

    private final SubtaskService subtaskService;
    private final ProjectService projectService;
    private final TaskService taskService;

    public SubtaskController(SubtaskService subtaskService, ProjectService projectService, TaskService taskService) {
        this.subtaskService = subtaskService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    @GetMapping("/{task_id}")
    public String showSubtasks(Model model, @PathVariable("task_id") int taskId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        List<Subtask> subtasks = subtaskService.getSubtasksByTaskID(taskId);
        int projectId = projectService.getProjectID(taskId);

        model.addAttribute("subtasks", subtasks);
        model.addAttribute("project_id", projectId);
        return "Subtask/subtasks";
    }

    @GetMapping("/create/{task_id}")
    public String showCreateSubtask(Model model, @PathVariable("task_id") int taskId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        Subtask subtask = new Subtask();
        Task task = taskService.getTaskById(taskId);

        model.addAttribute("subtask", subtask);
        model.addAttribute("task_id", taskId);
        model.addAttribute("task_start_date", task.getStart_date());
        model.addAttribute("task_end_date", task.getEnd_date());
        return "Subtask/createSubtask";
    }

    @PostMapping("/create/{task_id}")
    public String createSubtask(@ModelAttribute("subtask") Subtask subtask, @PathVariable("task_id") int taskId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        subtaskService.createSubtask(subtask, taskId);
        return "redirect:/subtasks/" + taskId;
    }

    @GetMapping("/{task_id}/edit/{subtask_id}")
    public String showEditSubtask(Model model, @PathVariable("subtask_id") int subtaskId, @PathVariable("task_id") int taskId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        Subtask subtask = subtaskService.getSubtaskbyIDs(subtaskId, taskId);
        Task task = taskService.getTaskById(taskId);

        model.addAttribute("subtask", subtask);
        model.addAttribute("subtask_id", subtaskId);
        model.addAttribute("task_id", taskId);
        model.addAttribute("task_start_date", task.getStart_date());
        model.addAttribute("task_end_date", task.getEnd_date());
        return "Subtask/editSubtask";
    }

    @PostMapping("/{task_id}/edit/{subtask_id}")
    public String editSubtask(@PathVariable("subtask_id") int subtaskId, @PathVariable("task_id") int taskId, @ModelAttribute("subtask") Subtask updatedSubtask, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        Subtask subtask = subtaskService.getSubtaskbyIDs(subtaskId, taskId);

        subtask.setSubtask_name(updatedSubtask.getSubtask_name());
        subtask.setHours(updatedSubtask.getHours());
        subtask.setStatus(updatedSubtask.getStatus());

        if (updatedSubtask.getStart_date() != null) {
            subtask.setStart_date(updatedSubtask.getStart_date());
        }

        if (updatedSubtask.getEnd_date() != null) {
            subtask.setEnd_date(updatedSubtask.getEnd_date());
        }

        subtaskService.editSubtask(subtask, subtaskId, taskId);
        return "redirect:/subtasks/" + taskId;
    }

    @GetMapping("/delete/{subtask_id}")
    public String showDeleteSubtask(Model model, @PathVariable("subtask_id") int subtaskId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        Subtask subtask = subtaskService.getSubtaskByID(subtaskId);
        model.addAttribute("subtask", subtask);
        return "Subtask/deleteSubtask";
    }

    @PostMapping("/delete/{subtask_id}")
    public String deleteSubtask(@PathVariable("subtask_id") int subtaskId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/sessionTimeout";
        }

        int taskId = subtaskService.getSubtaskByID(subtaskId).getTask_id();
        subtaskService.deleteSubtask(subtaskId);
        return "redirect:/subtasks/" + taskId;
    }
}
