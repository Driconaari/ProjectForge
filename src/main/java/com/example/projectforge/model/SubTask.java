package com.example.projectforge.model;

import jakarta.persistence.*;

@Entity
@Table(name = "SubTasks")
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubTaskID")
    private int subTaskID;

    @Column(name = "SubTaskName")
    private String subTaskName;

    @Column(name = "Description")
    private String description;

    @Column(name = "Deadline")
    private String deadline;

    // Getters and Setters
    public int getSubTaskID() {
        return subTaskID;
    }

    public void setSubTaskID(int subTaskID) {
        this.subTaskID = subTaskID;
    }

    public String getSubTaskName() {
        return subTaskName;
    }

    public void setSubTaskName(String subTaskName) {
        this.subTaskName = subTaskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
