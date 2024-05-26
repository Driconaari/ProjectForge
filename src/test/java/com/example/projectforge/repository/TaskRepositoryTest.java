package com.example.projectforge.repository;


import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ComponentScan("com.example.ProjectForge.repository.TaskRepository")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testCreateTask() {
        //Test data
        int project_id = 10;
        Task task = new Task(1, "Test Task", 5.0, LocalDate.now(), LocalDate.now(), 1, project_id);

        //Create task method
        taskRepository.createTask(task, project_id);

        //Verify the task is created
        assertNotNull(task.getTask_id());
        assertEquals("Test Task", task.getTask_name());
        assertEquals(5.0, task.getHours());
        assertEquals(LocalDate.now(), task.getStart_date());
        assertEquals(LocalDate.now(), task.getEnd_date());
        assertEquals(1, task.getStatus());
    }

    @Test
    public void testEditTask() {
        // Test data
        int project_id = 10;
        Task task = new Task(1, "Test Task", 5.0, LocalDate.now(), LocalDate.now(), 1, project_id);

        //Create task method
        taskRepository.createTask(task, project_id);

        //Edit task values
        task.setTask_name("Updated Task");
        task.setHours(8.0);
        task.setStart_date(LocalDate.now().plusDays(1));
        task.setEnd_date(LocalDate.now().plusDays(3));
        task.setStatus(2);

        //Edit task method
        taskRepository.editTask(task, task.getTask_id(), project_id);

        //Get updated task
        Task updatedTask = taskRepository.getTaskbyTaskId(task.getTask_id());

        //Verify task is updated
        assertNotNull(updatedTask);
        assertEquals(task.getTask_name(), updatedTask.getTask_name());
        assertEquals(task.getHours(), updatedTask.getHours(), 0.0);
        assertEquals(task.getStart_date(), updatedTask.getStart_date());
        assertEquals(task.getEnd_date(), updatedTask.getEnd_date());
        assertEquals(task.getStatus(), updatedTask.getStatus());
    }

    @Test
    public void testDeleteTask() {
        //Test data
        int project_id = 10;
        Task task = new Task(1, "Test Task", 5.0, LocalDate.now(), LocalDate.now().plusDays(1), 1, project_id);

        //Create task method
        taskRepository.createTask(task, project_id);

        //Delete task method
        taskRepository.deleteTask(task.getTask_id());

        //Verify task is deleted
        assertNull(taskRepository.getTaskbyTaskId(task.getTask_id()));
    }
}