package com.example.projectforge.controller;

import com.example.projectforge.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourcesController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/resources")
    public String displayResources(Model model) {
        model.addAttribute("resourceAllocations", resourceService.getAllResourceAllocations());
        model.addAttribute("resources", resourceService.getAllResources());
        return "resources";
    }
}