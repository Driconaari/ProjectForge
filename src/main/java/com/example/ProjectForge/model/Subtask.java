package com.example.ProjectForge.model;

import java.time.LocalDate;

public class Subtask {

    private int subtask_id;
    private String subtask_name;
    private double hours;
    private LocalDate start_date;
    private LocalDate end_date;
    private int status;
    private int task_id;


    // New fields
    private long startOffset;
    private long duration;

    public Subtask(int subtask_id, String subtask_name, double hours, LocalDate start_date, LocalDate end_date, int status, int task_id) {
        this.subtask_id = subtask_id;
        this.subtask_name = subtask_name;
        this.hours = hours;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.task_id = task_id;
    }

    //Used to add calculatedTime to task
    public Subtask(String subtaskName, LocalDate startDate, LocalDate endDate) {
        this.subtask_name = subtaskName;
        this.start_date = startDate != null ? startDate : LocalDate.now(); // Default to today if null
        this.end_date = endDate != null ? endDate : this.start_date.plusDays(1); // Default to start date + 1 day if null
    }

    //Default constructor
    public Subtask() {
    }

    public int getSubtask_id() {
        return subtask_id;
    }

    public void setSubtask_id(int subtask_id) {
        this.subtask_id = subtask_id;
    }

    public String getSubtask_name() {
        return subtask_name;
    }

    public void setSubtask_name(String subtask_name) {
        this.subtask_name = subtask_name;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
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

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "subtask_id=" + subtask_id +
                ", subtask_name='" + subtask_name + '\'' +
                ", hours=" + hours +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", status=" + status +
                ", task_id=" + task_id +
                '}';
    }


    // New getters and setters
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
