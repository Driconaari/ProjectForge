package com.example.ProjectForge.repository;

import com.example.ProjectForge.dto.TaskSubtaskDTO;
import com.example.ProjectForge.model.Task;

import java.util.List;

public interface ITaskRepository {

    //get task from project_id
    public List<Task> getTaskByProjectID(int project_id);

    //view task by task_id
    public void createTask(Task task, int project_id);

    //Edit task by task_id and project_id
    public void editTask(Task task, int task_id, int project_id);

    //Get task by task_id and project_id
    public Task getTaskByIDs(int task_id, int project_id);

    //Delete task by task_id
    public void deleteTask(int task_id);

    //Get task by task_id
    public Task getTaskbyTaskId(int task_id);

    //Get project_id by task_id
    public int getProjectIDbyTaskID(int task_id);

    //Get calculated time for task by task_id
    public Double getProjectTimeByTaskID(int task_id);

    //Get task subtasks by project_id
    public List<TaskSubtaskDTO> getTaskSubtasksByProID(int project_id);


    List<Task> getTasksWithSubtasksByProjectID(int projectId);
}
