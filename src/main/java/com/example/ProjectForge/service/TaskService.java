package com.example.ProjectForge.service;

import com.example.ProjectForge.dto.TaskSubtaskDTO;
import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
       this.taskRepository = taskRepository;
    }

    //Create Task
    public void createTask(Task task, int project_id)  {
        taskRepository.createTask(task, project_id);
    }

    //Get Task by project_id
    public List<Task> getTaskByProID(int project_id) {
        return taskRepository.getTaskByProjectID(project_id);
    }

    //Edit task
    public void editTask(Task task, int task_id, int project_id) {
        taskRepository.editTask(task, task_id, project_id);
    }

    //Get project from user_id and user_id
    public Task getTaskByIDs(int task_id, int project_id) {
        return taskRepository.getTaskByIDs(task_id, project_id);
    }

    //Delete Task
    public void deleteTask(int task_id) {
        taskRepository.deleteTask(task_id);
    }

    //Get Task from task_id
    public Task getTaskById(int taskId) {
       return taskRepository.getTaskbyTaskId(taskId);
    }

    //Get project_id by task_id
    public int getProIDbyTaskID(int task_id) {
        return taskRepository.getProjectIDbyTaskID(task_id);
    }

    //Calculated time for task and subtask
    public Double getProjectTimeByTaskID (int task_id) {
        return taskRepository.getProjectTimeByTaskID(task_id);
    }

    //Get task and subtask by project_id
    public List<TaskSubtaskDTO> getTaskSubtasksByProID(int project_id) {
        return taskRepository.getTaskSubtasksByProID(project_id);
    }

}
