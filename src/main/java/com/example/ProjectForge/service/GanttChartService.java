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
            long startOffsetDays = ChronoUnit.DAYS.between(projectStartDate, task.getStart_date());
            long durationDays = ChronoUnit.DAYS.between(task.getStart_date(), task.getEnd_date());
            task.setStartOffset(startOffsetDays * 10); // assuming 10px per day
            task.setDuration(durationDays * 10); // assuming 10px per day

            for (Subtask subtask : task.getSubtasks()) {
                long subtaskStartOffsetDays = ChronoUnit.DAYS.between(projectStartDate, subtask.getStart_date());
                long subtaskDurationDays = ChronoUnit.DAYS.between(subtask.getStart_date(), subtask.getEnd_date());
                subtask.setStartOffset(subtaskStartOffsetDays * 10); // assuming 10px per day
                subtask.setDuration(subtaskDurationDays * 10); // assuming 10px per day
            }
        }
        return tasks;
    }
}



