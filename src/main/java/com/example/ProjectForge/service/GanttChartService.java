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
            // Ensure startDate and endDate are not null
            LocalDate taskStartDate = task.getStart_date() != null ? task.getStart_date() : projectStartDate;
            LocalDate taskEndDate = task.getEnd_date() != null ? task.getEnd_date() : taskStartDate.plusDays(1);

            long startOffset = ChronoUnit.DAYS.between(projectStartDate, taskStartDate);
            long duration = ChronoUnit.DAYS.between(taskStartDate, taskEndDate);
            task.setStartOffset(startOffset * 10); // Multiply by 10 to adjust for better visualization
            task.setDuration(duration * 10);

            for (Subtask subtask : task.getSubtasks()) {
                // Ensure startDate and endDate are not null
                LocalDate subtaskStartDate = subtask.getStart_date() != null ? subtask.getStart_date() : taskStartDate;
                LocalDate subtaskEndDate = subtask.getEnd_date() != null ? subtask.getEnd_date() : subtaskStartDate.plusDays(1);

                long subtaskStartOffset = ChronoUnit.DAYS.between(projectStartDate, subtaskStartDate);
                long subtaskDuration = ChronoUnit.DAYS.between(subtaskStartDate, subtaskEndDate);
                subtask.setStartOffset(subtaskStartOffset * 10);
                subtask.setDuration(subtaskDuration * 10);
            }
        }
        return tasks;
    }
}
