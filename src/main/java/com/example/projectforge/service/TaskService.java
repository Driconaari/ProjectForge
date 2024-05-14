package com.example.projectforge.service;

import com.example.projectforge.dto.TaskDto;
import com.example.projectforge.model.Task;
import com.example.projectforge.projectRepository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public void createTask(TaskDto taskDto) {
        Task newTask = new Task();
        newTask.setTaskName(taskDto.getTaskName());
        newTask.setDescription(taskDto.getDescription());
        newTask.setDeadline(taskDto.getDeadline());
        System.out.println("Creating task: " + newTask); // Add logging here
        taskRepository.save(newTask);
    }
}


