package com.example.ProjectForge.controller;

import com.example.ProjectForge.model.GanttModel;
import com.example.ProjectForge.service.GanttService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/api/gantt")
public class GanttController {
    private final GanttService ganttService;

    public GanttController(GanttService ganttService) {
        this.ganttService = ganttService;
    }

    @GetMapping("/{userId}")
    public String getAllGanttModelsForUser(@PathVariable int userId, Model model) {
        List<GanttModel> ganttModels = ganttService.getAllGanttModelsForUser(userId);
        model.addAttribute("ganttModels", ganttModels);
        return "gantt";
    }



    @GetMapping("/{userId}/{projectId}")
    public String getGanttModel(@PathVariable int userId, @PathVariable int projectId, Model model) {
        GanttModel ganttModel = ganttService.getGanttModel(userId, projectId);
        model.addAttribute("ganttModel", ganttModel);
        return "gantt";
    }
}
