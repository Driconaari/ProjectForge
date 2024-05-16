package com.example.projectforge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Repository("/project")

public class GanttController {

    @GetMapping("/gantt")
    public String displayGanttChart() {
        return "gantt";
    }
}