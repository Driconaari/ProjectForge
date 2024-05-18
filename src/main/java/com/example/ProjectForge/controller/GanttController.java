package com.example.ProjectForge.controller;

import com.example.ProjectForge.model.GanttModel;
import com.example.ProjectForge.service.GanttService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gantt")
public class GanttController {
    private final GanttService ganttService;

    public GanttController(GanttService ganttService) {
        this.ganttService = ganttService;
    }

    // Assume GanttService is injected via constructor

    @GetMapping("/{userId}/{projectId}")
    public GanttModel getGanttModel(@PathVariable int userId, @PathVariable int projectId) {
        return ganttService.getGanttModel(userId, projectId);
    }
}
