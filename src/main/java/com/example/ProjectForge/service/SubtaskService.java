package com.example.ProjectForge.service;

import com.example.ProjectForge.model.Subtask;
import com.example.ProjectForge.repository.SubtaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtaskService {

    private SubtaskRepository subtaskRepository;

    public SubtaskService (SubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }

    //Create subtask
    public void createSubtask(Subtask subtask, int task_id) {
        subtaskRepository.createSubtask(subtask, task_id);
    }

    //Get subtask by taskID
    public List<Subtask> getSubtasksByTaskID(int task_id) {
       return subtaskRepository.getSubtasksByTaskID(task_id);
    }

    //Delete subtask
    public void deleteSubtask(int subtask_id) {
        subtaskRepository.deleteSubtask(subtask_id);
    }

    //Get subtask from subtask_id
    public Subtask getSubtaskByID(int subtask_id) {
        return subtaskRepository.getSubtaskByID(subtask_id);
    }


    //Get subtask by IDs
    public Subtask getSubtaskbyIDs(int subtask_id, int task_id) {
        return subtaskRepository.getSubtaskByIDs(subtask_id, task_id);
    }

    //Edit subtask
    public void editSubtask(Subtask subtask, int subtask_id, int task_id) {
        subtaskRepository.editSubtask(subtask, subtask_id, task_id);
    }
}
