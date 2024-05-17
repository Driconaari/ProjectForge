package com.example.ProjectForge.repository;

import com.example.ProjectForge.model.Subtask;

import java.util.List;

public interface ISubtaskRepository {

    //Get subtasks by task_id
    public List<Subtask> getSubtasksByTaskID(int task_id);

    //Create subtask by task_id
    public Subtask createSubtask(Subtask subtask, int task_id);

    //Edit subtask by subtask_id and task_id
    public void editSubtask(Subtask subtask, int subtask_id, int task_id);

    //Get subtask by subtask_id and task_id
    public Subtask getSubtaskByIDs(int subtask_id, int task_id);

    //Delete subtask by subtask_id
    public void deleteSubtask(int subtask_id);

    //Get subtask by subtask_id
    public Subtask getSubtaskByID(int subtask_id);
}
