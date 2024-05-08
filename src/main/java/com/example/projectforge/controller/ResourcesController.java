package com.example.projectforge.controller;

import com.example.projectforge.model.Resource;
import com.example.projectforge.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ResourcesController {

    @Autowired
    private ResourceService resourceService;
@GetMapping("/resources")
public String displayResources(Model model) {
    model.addAttribute("resourceAllocations", resourceService.getAllResourceAllocations());
    model.addAttribute("resources", resourceService.getAllResources());
    model.addAttribute("showForm", false); // Add this line
    return "resources";
}


  @PostMapping("/resources")
public String addResource(@ModelAttribute Resource resource, Model model) {
    resourceService.saveResource(resource);
    model.addAttribute("showForm", true); // Add this line
    return "redirect:/resources";
}
}
