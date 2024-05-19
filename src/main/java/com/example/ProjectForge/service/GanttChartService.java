package com.example.ProjectForge.service;

import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.model.Subtask;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class GanttChartService {

    public List<Task> calculateOffsetsAndDurations(List<Task> tasks, LocalDate projectStartDate) {
        for (Task task : tasks) {
            long startOffset = ChronoUnit.DAYS.between(projectStartDate, task.getStart_date());
            long duration = ChronoUnit.DAYS.between(task.getStart_date(), task.getEnd_date());
            task.setStartOffset(startOffset * 10); // Multiply by 10 to adjust for better visualization
            task.setDuration(duration * 10);

            for (Subtask subtask : task.getSubtasks()) {
                long subtaskStartOffset = ChronoUnit.DAYS.between(projectStartDate, subtask.getStart_date());
                long subtaskDuration = ChronoUnit.DAYS.between(subtask.getStart_date(), subtask.getEnd_date());
                subtask.setStartOffset(subtaskStartOffset * 10);
                subtask.setDuration(subtaskDuration * 10);
            }
        }
        return tasks;
    }
}
