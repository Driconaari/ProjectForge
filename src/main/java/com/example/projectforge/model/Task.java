package com.example.projectforge.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "tasks")

public class Task {
    @Id
    private int taskId;
    private int subprojectId;
    private String taskName;
    private String description;
    private int duration;
    private String resourceRequirement;
    private Date deadline;

    @ManyToOne
    private Project parentProject;

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setSubprojectId(int subprojectId) {
        this.subprojectId = subprojectId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setResourceRequirement(String resourceRequirement) {
        this.resourceRequirement = resourceRequirement;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getSubprojectId() {
        return subprojectId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public String getResourceRequirement() {
        return resourceRequirement;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Project getParentProject() {
        return parentProject;
    }

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }
}