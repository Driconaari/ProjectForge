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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;


import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {

    TaskService taskService;
    ProjectService projectService;
    UserService userService;
    ProjectRepository projectRepository;

    public TaskController(TaskService taskService, UserService userService, ProjectService projectService, ProjectRepository projectRepository) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }

    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    //Get tasks
    @GetMapping(path = "tasks/{project_id}")
    public String showTasks(Model model, @PathVariable int project_id, HttpSession session) {
        if (isLoggedIn(session)) {
            List<Task> tasks = taskService.getTaskByProID(project_id);
            int user_id = userService.getUserID(project_id);

            // Calculate project time and task time
            double projectCalculatedTime = projectService.getProjectTimeByProjectID(project_id);
            for (Task task : tasks) {
                double taskCalculatedTime = taskService.getProjectTimeByTaskID(task.getTask_id());
                task.setCalculatedTime(taskCalculatedTime);

                // Fetch the Project object and set it in the Task object
                Optional<Project> projectOptional = projectRepository.findById(task.getProject_id());
                if (projectOptional.isPresent()) {
                    Project project = projectOptional.get();
                    task.setProject(project);
                }
            }

            model.addAttribute("tasks", tasks);
            model.addAttribute("user_id", user_id);
            model.addAttribute("projectCalculatedTime", projectCalculatedTime);
            return "Task/tasks";
        }
        return "redirect:/sessionTimeout";
    }


    //Create task page
@GetMapping(path = "/task/create/{project_id}")
public String showCreateTask(Model model, @PathVariable int project_id, HttpSession session) {
    if (isLoggedIn(session)) {
        model.addAttribute("task", new Task()); // Add the task object to the model
        model.addAttribute("project_id", project_id);
        return "Task/createTask";
    }
    return "redirect:/sessionTimeout";
}

    //Create task

@PostMapping(path = "task/create/{project_id}")
public String createTask(@ModelAttribute("task") Task task, @PathVariable("project_id") int project_id, Model model, HttpSession session) {
    if (isLoggedIn(session)) {
        model.addAttribute("task", task); // Add the task object to the model
        System.out.println(task); // Log the task object
        taskService.createTask(task, project_id);
        return "redirect:/tasks/" + project_id;
    }
    return "redirect:/sessionTimeout";
}


    //Edit task page
@GetMapping(path = "/task/{project_id}/edit/{task_id}")
public String showEditTask(Model model, @PathVariable int task_id, @PathVariable int project_id, HttpSession session) {
    if (isLoggedIn(session)) {
        Task task = taskService.getTaskByIDs(task_id, project_id);
        Project project = projectService.getProjectByProjectID(project_id);

        model.addAttribute("task", task); // Add the task object to the model
        model.addAttribute("task_id", task_id);
        model.addAttribute("project_id", project_id);
        model.addAttribute("project_start_date", project.getStart_date());
        model.addAttribute("project_end_date", project.getEnd_date());
        return "Task/editTask";
    }
    return "redirect:/sessionTimeout";
}

    //Edit task
   @PostMapping(path = "/task/{project_id}/edit/{task_id}")
public String editTask(@PathVariable int task_id, @PathVariable int project_id, @Valid @ModelAttribute Task updatedTask, BindingResult result, HttpSession session) {
    if (isLoggedIn(session)) {
        if (result.hasErrors()) {
            return "Task/editTask";
        }
        Task task = taskService.getTaskByIDs(task_id, project_id);

        task.setTask_name(updatedTask.getTask_name());
        task.setHours(updatedTask.getHours());
        task.setStatus(updatedTask.getStatus());

        // Check and update start_date if not null
        LocalDate updatedStartDate = updatedTask.getStart_date();
        if (updatedStartDate != null) {
            task.setStart_date(updatedStartDate);
        }

        // Check and update end_date if not null
        LocalDate updatedEndDate = updatedTask.getEnd_date();
        if (updatedEndDate != null) {
            task.setEnd_date(updatedEndDate);
        }

        taskService.editTask(task, task_id, project_id);
        return "redirect:/tasks/" + project_id;
    }
    return "redirect:/sessionTimeout";
}

    //Delete task page
    @GetMapping(path = "task/delete/{task_id}")
    public String deleteTask(@PathVariable("task_id") int task_id, Model model, HttpSession session) {
        if (isLoggedIn(session)) {
            Task task = taskService.getTaskById(task_id);

            model.addAttribute("task_id", task_id);
            model.addAttribute("task", task);
            return "Task/deleteTask";
        }
        return "redirect:/sessionTimeout";
    }

    //Delete task
    @PostMapping(path = "task/delete/{task_id}")
    public String removeTask(@PathVariable("task_id") int task_id, Model model, HttpSession session) {
        if (isLoggedIn(session)) {
            int projectID = taskService.getProjectIDbyTaskID(task_id);
            taskService.deleteTask(task_id);
            return "redirect:/tasks/" + projectID;
        }
        return "redirect:/sessionTimeout";
    }


    //get project with task and subtasks by project_id and show in taskSubtask page
    @GetMapping(path = "project/{project_id}")
    public String showProject(Model model, @PathVariable int project_id, HttpSession session) {

        if (isLoggedIn(session)) {
            List<TaskSubtaskDTO> taskSubtasks = taskService.getTaskSubtasksByProID(project_id);
            double projectCalculatedTime = projectService.getProjectTimeByProjectID(project_id);

            int user_id = userService.getUserID(project_id);

            double taskCalculatedTime1 = projectService.getProjectTimeByProjectID(project_id);
            for (TaskSubtaskDTO task : taskSubtasks) {
                double taskCalculatedTime2 = taskService.getProjectTimeByTaskID(task.getId());
                task.setCalculatedTime(taskCalculatedTime2);
            }

            model.addAttribute("taskSubtask", taskSubtasks);
            model.addAttribute("projectCalculatedTime", projectCalculatedTime);
            model.addAttribute("taskCalculatedTime", taskCalculatedTime1);
            model.addAttribute("user_id", user_id);

            return "Task/taskSubtask";
        }
        return "redirect:/sessionTimeout";
    }
}