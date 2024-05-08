package com.example.projectforge.service;

import com.example.projectforge.ResourceRepository.ResourceAllocationRepository;
import com.example.projectforge.ResourceRepository.ResourceRepository;
import com.example.projectforge.model.ResourceAllocation;
import com.example.projectforge.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceAllocationRepository resourceAllocationRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    public List<ResourceAllocation> getAllResourceAllocations() {
        return resourceAllocationRepository.findAll();
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public void saveResource(Resource resource) {
        resourceRepository.save(resource);
    }
}