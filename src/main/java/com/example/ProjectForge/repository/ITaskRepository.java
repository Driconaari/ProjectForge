package com.example.ProjectForge.repository;

import com.example.ProjectForge.dto.TaskSubtaskDTO;
import com.example.ProjectForge.model.Task;

import java.util.List;

public interface ITaskRepository {

    //get task from project_id
    public List<Task> getTaskByProjectID(int project_id);

    //view task
    public void createTask(Task task, int project_id);

    //Edit task
    public void editTask(Task task, int task_id, int project_id);

    //Get project from user_id and user_id
    public Task getTaskByIDs(int task_id, int project_id);

    //Delete task
    public void deleteTask(int task_id);

    //Get task from task_id
    public Task getTaskbyTaskId(int task_id);

    //Get project_id from task_id
    public int getProjectIDbyTaskID(int task_id);

    //Get calculated time for task and subtask
    public Double getProjectTimeByTaskID(int task_id);

    //Get task and subtask from project
    public List<TaskSubtaskDTO> getTaskSubtasksByProID(int project_id);

}
