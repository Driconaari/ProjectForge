package com.example.projectforge.model;

import jakarta.persistence.*;

@Entity
@Table(name = "resourceallocation")
public class ResourceAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AllocationID")
    private int allocationID;

    @Column(name = "TaskID")
    private int taskID;

    @Column(name = "ResourceID")
    private int resourceID;

    @Column(name = "HoursAllocated")
    private int hoursAllocated;

    // Constructors
    public ResourceAllocation() {
        // Default constructor
    }

    public ResourceAllocation(int allocationID, int taskID, int resourceID, int hoursAllocated) {
        this.allocationID = allocationID;
        this.taskID = taskID;
        this.resourceID = resourceID;
        this.hoursAllocated = hoursAllocated;
    }

    // Getters and Setters
    public int getAllocationID() {
        return allocationID;
    }

    public void setAllocationID(int allocationID) {
        this.allocationID = allocationID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public int getHoursAllocated() {
        return hoursAllocated;
    }

    public void setHoursAllocated(int hoursAllocated) {
        this.hoursAllocated = hoursAllocated;
    }
}