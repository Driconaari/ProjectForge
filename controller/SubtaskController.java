package com.example.ProjectForge.controller;

import com.example.ProjectForge.model.Subtask;
import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.service.ProjectService;
import com.example.ProjectForge.service.SubtaskService;
import com.example.ProjectForge.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class SubtaskController {

    private SubtaskService subtaskService;
    private ProjectService projectService;
    private TaskService taskService;

    public SubtaskController(SubtaskService subtaskService, ProjectService projectService, TaskService taskService) {
        this.subtaskService = subtaskService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    public boolean isSignedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }


    @GetMapping(path = "subtasks/{task_id}")
    public String showSubtasks(Model model, @PathVariable int task_id, HttpSession session) {

        if (isSignedIn(session)) {
            List<Subtask> subtasks = subtaskService.getSubtasksByTaskID(task_id);
            int project_id = projectService.getProjectID(task_id);

            model.addAttribute("subtasks", subtasks);
            model.addAttribute("project_id", project_id);
            return "Subtask/subtasks";
        }
        return "redirect:/sessionTimeout";

    }

    //Create subtask page
    @GetMapping(path = "subtasks/create/{task_id}")
    public String showCreateSubtask(Model model, @PathVariable("task_id") int task_id, HttpSession session) {
        if (isSignedIn(session)) {
            Subtask subtask = new Subtask();
            Task task = taskService.getTaskById(task_id);

            model.addAttribute("subtask", subtask);
            model.addAttribute("task_id", task_id);
            model.addAttribute("task_start_date", task.getStart_date());
            model.addAttribute("task_end_date", task.getEnd_date());
            return "Subtask/createSubtask";
        }
        return "redirect:/sessionTimeout";
    }

    //Create subtask
    @PostMapping(path = "subtasks/create/{task_id}")
    public String createSubtask(@ModelAttribute("subtask") Subtask subtask, @PathVariable("task_id") int task_id, HttpSession session) {
        if (isSignedIn(session)) {
            subtaskService.createSubtask(subtask, task_id);
            return "redirect:/subtasks/" + task_id;
        }
        return "redirect:/sessionTimeout";
    }

    //Edit subtask page
    @GetMapping(path = "/subtask/{task_id}/edit/{subtask_id}")
    public String showEditSubtask(Model model, @PathVariable int subtask_id, @PathVariable int task_id, HttpSession session) {
        if (isSignedIn(session)) {
            Subtask subtask = subtaskService.getSubtaskbyIDs(subtask_id, task_id);
            Task task = taskService.getTaskById(task_id);

            model.addAttribute("subtask", subtask);
            model.addAttribute("subtask_id", subtask_id);
            model.addAttribute("task_id", task_id);
            model.addAttribute("task_start_date", task.getStart_date());
            model.addAttribute("task_end_date", task.getEnd_date());
            return "Subtask/editSubtask";
        }
        return "redirect:/sessionTimeout";
    }

    //Edit subtask
    @PostMapping(path = "/subtask/{task_id}/edit/{subtask_id}")
    public String editSubtask(@PathVariable int subtask_id, @PathVariable int task_id, @ModelAttribute Subtask updatedSubtask, HttpSession session) {

        if(isSignedIn(session)) {
            Subtask subtask = subtaskService.getSubtaskbyIDs(subtask_id, task_id);

            subtask.setSubtask_name(updatedSubtask.getSubtask_name());
            subtask.setHours(updatedSubtask.getHours());
            subtask.setStatus(updatedSubtask.getStatus());

            // Check and update start_date if not null
            LocalDate updatedStartDate = updatedSubtask.getStart_date();
            if (updatedStartDate != null) {
                subtask.setStart_date(updatedStartDate);
            }

            // Check and update end_date if not null
            LocalDate updatedEndDate = updatedSubtask.getEnd_date();
            if (updatedEndDate != null) {
                subtask.setEnd_date(updatedEndDate);
            }

            subtaskService.editSubtask(subtask, subtask_id, task_id);
            return "redirect:/subtasks/" + task_id;
        }
        return "redirect:/sessionTimeout";
    }

    //Delete subtask page
    @GetMapping(path = "subtasks/delete/{subtask_id}")
    public String showDeleteSubtask(Model model, @PathVariable("subtask_id") int subtask_id, HttpSession session) {
        if (isSignedIn(session)) {
            Subtask subtask = subtaskService.getSubtaskByID(subtask_id);
            model.addAttribute("subtask", subtask);
            return "Subtask/deleteSubtask";
        }
        return "redirect:/sessionTimeout";
    }

    //Delete Subtask
    @PostMapping(path = "subtasks/delete/{subtask_id}")
    public String deleteSubtask(@PathVariable("subtask_id") int subtask_id, HttpSession session) {
        if (isSignedIn(session)) {
            int task_id = subtaskService.getSubtaskByID(subtask_id).getTask_id();
            subtaskService.deleteSubtask(subtask_id);
            return "redirect:/subtasks/" + task_id;
        }
        return "redirect:/sessionTimeout";
    }

}
