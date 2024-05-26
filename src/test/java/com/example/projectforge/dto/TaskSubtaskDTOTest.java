package com.example.projectforge.dto;

import com.example.ProjectForge.dto.TaskSubtaskDTO;
import com.example.ProjectForge.model.Subtask;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskSubtaskDTOTest {

    // Test TaskSubtaskDTO class methods and constructors using JUnit test
    // framework and assertEquals method from org.junit.jupiter.api.Assertions '
    // package to compare the expected and actual values of the TaskSubtaskDTO object
    @Test
    public void testTaskSubtaskDTO() {
        TaskSubtaskDTO taskSubtaskDTO = new TaskSubtaskDTO();
        taskSubtaskDTO.setId(1);
        taskSubtaskDTO.setName("Test Task");
        taskSubtaskDTO.setHours(8.0);
        taskSubtaskDTO.setStart_date(LocalDate.now());
        taskSubtaskDTO.setEnd_date(LocalDate.now().plusDays(1));
        taskSubtaskDTO.setStatus(1);
        taskSubtaskDTO.setCalculatedTime(8.0);
        taskSubtaskDTO.setProject_id(1);
        taskSubtaskDTO.setSubtasks(Collections.singletonList(new Subtask()));

        assertEquals(1, taskSubtaskDTO.getId());
        assertEquals("Test Task", taskSubtaskDTO.getName());
        assertEquals(8.0, taskSubtaskDTO.getHours());
        assertEquals(LocalDate.now(), taskSubtaskDTO.getStart_date());
        assertEquals(LocalDate.now().plusDays(1), taskSubtaskDTO.getEnd_date());
        assertEquals(1, taskSubtaskDTO.getStatus());
        assertEquals(8.0, taskSubtaskDTO.getCalculatedTime());
        assertEquals(1, taskSubtaskDTO.getProject_id());
        assertEquals(1, taskSubtaskDTO.getSubtasks().size());
    }
}