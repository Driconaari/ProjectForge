package com.example.ProjectForge.model;

import java.util.List;

public class GanttModel {
    private User user;
    private Project project;
    private List<Task> tasks;

    // getters and setters


    public User getUser() {
        return user;
    }

    public Project getProject() {
        return project;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}