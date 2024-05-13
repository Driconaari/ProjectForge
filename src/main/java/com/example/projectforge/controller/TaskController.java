package com.example.projectforge.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class TaskController {


    //tasks
    @GetMapping("/addTask")
    public String showCreateTaskPage() {
        return "addTask"; // Return the name of the HTML file (without the extension)
    }

    @GetMapping("/addSubTask")
    public String showCreateSubTaskPage() {
        return "addSubTask"; // Return the name of the HTML file (without the extension)
    }
}
