package com.example.ProjectForge.model;

import java.time.LocalDate;
import java.util.List;

public class Project {

    private List<Task> tasks;
    private long startOffset;
    private long duration;

    private int project_id;
    private String project_name;
    private String project_description;
    private LocalDate start_date;
    private LocalDate end_date;
    private int user_id;

    private double projectCalculatedTime;

    public Project(int project_id, String project_name, String project_description, LocalDate start_date, LocalDate end_date, int user_id) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.project_description = project_description;
        this.user_id = user_id;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    //Default constructor
    public Project() {

    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }


    public String getProject_description() {
        return project_description;
    }

    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public double getProjectCalculatedTime() {
        return projectCalculatedTime;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks; // Add this line
    }

    // Add a getter for tasks
    public List<Task> getTasks() {
        return tasks;
    }

    public void setProjectCalculatedTime(double projectCalculatedTime) {
        this.projectCalculatedTime = projectCalculatedTime;
    }

    // Add a getter for startOffset
    public long getStartOffset() {
        return startOffset;
    }

    // Add a getter for duration
    public long getDuration() {
        return this.duration;
    }

    // Add a setter for startOffset
    public void setStartOffset(long startOffset) {
        this.startOffset = startOffset;
    }

    // Add a setter for duration
    public void setDuration(long duration) {
        this.duration = duration;
    }
}
