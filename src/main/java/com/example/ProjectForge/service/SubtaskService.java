package com.example.ProjectForge.service;

import com.example.ProjectForge.model.Subtask;
import com.example.ProjectForge.repository.SubtaskRepositoryDB;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtaskService {

    private SubtaskRepositoryDB subtaskRepositoryDB;

    public SubtaskService (SubtaskRepositoryDB subtaskRepositoryDB) {
        this.subtaskRepositoryDB=subtaskRepositoryDB;
    }

    //Create subtask
    public void createSubtask(Subtask subtask, int task_id) {
        subtaskRepositoryDB.createSubtask(subtask, task_id);
    }

    //Get subtask by taskID
    public List<Subtask> getSubtasksByTaskID(int task_id) {
       return subtaskRepositoryDB.getSubtasksByTaskID(task_id);
    }

    //Delete subtask
    public void deleteSubtask(int subtask_id) {
        subtaskRepositoryDB.deleteSubtask(subtask_id);
    }

    //Get subtask from subtask_id
    public Subtask getSubtaskByID(int subtask_id) {
        return subtaskRepositoryDB.getSubtaskByID(subtask_id);
    }


    //Get subtask by IDs
    public Subtask getSubtaskbyIDs(int subtask_id, int task_id) {
        return subtaskRepositoryDB.getSubtaskByIDs(subtask_id, task_id);
    }

    //Edit subtask
    public void editSubtask(Subtask subtask, int subtask_id, int task_id) {
        subtaskRepositoryDB.editSubtask(subtask, subtask_id, task_id);
    }
}
