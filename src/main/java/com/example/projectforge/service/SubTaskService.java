package com.example.projectforge.service;

import com.example.projectforge.dto.SubTaskDto;
import com.example.projectforge.model.SubTask;
import com.example.projectforge.projectRepository.SubTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubTaskService {
    @Autowired
    private SubTaskRepository subTaskRepository;

    public void createSubTask(SubTaskDto subTaskDto) {
        SubTask newSubTask = new SubTask();
        newSubTask.setSubTaskName(subTaskDto.getSubTaskName());
        newSubTask.setDescription(subTaskDto.getDescription());
        newSubTask.setDeadline(subTaskDto.getDeadline());
        subTaskRepository.save(newSubTask);
    }
}
