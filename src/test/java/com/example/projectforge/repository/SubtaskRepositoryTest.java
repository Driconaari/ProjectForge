package com.example.projectforge.repository;

import com.example.ProjectForge.model.Subtask;
import com.example.ProjectForge.repository.SubtaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubtaskRepositoryTest {

    @Mock
    private SubtaskRepository subtaskRepository;

    @Test
    public void testCreateSubtask() {
        //Test data
        int task_id = 999999; // High number unlikely to exist
        Subtask subtask = new Subtask(999999, "Test Subtask", 10.0, LocalDate.now(), LocalDate.now(), 1, task_id);

        //Create subtask method
        subtaskRepository.createSubtask(subtask, task_id);

        //Verify the subtask is created
        verify(subtaskRepository, times(1)).createSubtask(subtask, task_id);
    }

    @Test
    public void testDeleteSubtask() {
        //Test data
        int subtask_id = 999999; // High number unlikely to exist

        //Delete subtask method
        subtaskRepository.deleteSubtask(subtask_id);

        //Verify subtask is deleted
        verify(subtaskRepository, times(1)).deleteSubtask(subtask_id);
    }

    @Test
    public void testEditSubtask() {
        //Test data
        int task_id = 999999; // High number unlikely to exist
        Subtask subtask = new Subtask(999999, "Test Subtask", 10.0, LocalDate.now(), LocalDate.now(), 1, task_id);

        //Edit subtask method
        subtaskRepository.editSubtask(subtask, subtask.getSubtask_id(), task_id);

        //Verify subtask is updated
        verify(subtaskRepository, times(1)).editSubtask(subtask, subtask.getSubtask_id(), task_id);
    }
}