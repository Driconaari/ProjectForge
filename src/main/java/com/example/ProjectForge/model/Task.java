package com.example.ProjectForge.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task {

    //List of subtasks for a task object (One-to-many relationship)
    private List<Subtask> subtasks;

    private int task_id;
    private String task_name;
    private double hours;
    private double calculatedTime;
    private LocalDate start_date;
    private LocalDate end_date;
    private int status;
    private int project_id;
    private Project project;

    // New fields for Gantt chart visualization
    private long startOffset;
    private long duration;


    //Used when receiving a task from the database
    public Task(int task_id, String task_name, double hours, LocalDate start_date, LocalDate end_date, int status, int project_id) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.hours = hours;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.project_id = project_id;
    }

    //Used to add calculatedTime to task
    public Task(int task_id, String task_name, double hours, double calculatedTime, LocalDate start_date, LocalDate end_date, int status, int project_id) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.hours = hours;
        this.calculatedTime = calculatedTime;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.project_id = project_id;
    }

    public Task(String taskName, LocalDate startDate, LocalDate endDate, List<Subtask> subtasks) {
        this.task_name = taskName;
        this.start_date = startDate != null ? startDate : LocalDate.now(); // Default to today if null
        this.end_date = endDate != null ? endDate : this.start_date.plusDays(1); // Default to start date + 1 day if null
        this.subtasks = subtasks;
    }

    //Default constructor
    public Task() {
    }


    //Getters and setters

    public Project getProject() {
        return project;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public double getCalculatedTime() {
        return calculatedTime;
    }

    public void setCalculatedTime(double calculatedTime) {
        this.calculatedTime = calculatedTime;
    }

    public LocalDate getStart_date() {
        return start_date;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    //Get subtasks for a task object (One-to-many relationship)
    public List<Subtask> getSubtasks() {
        if (subtasks == null) {
            subtasks = new ArrayList<>();
        }
        return subtasks;
    }

    //Set subtasks for a task object (One-to-many relationship)
    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }


    // Getters and setters for startOffset and duration
    public long getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(long startOffset) {
        this.startOffset = startOffset;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}

