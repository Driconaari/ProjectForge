package com.example.projectforge.model;


import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectID;
    @Column(name = "projectName")
    private String projectName;
    private String description;
    private Date deadline;

    @ManyToOne
    @JoinColumn(name = "parentProjectID")
    private Project parentProject;

    // Getters and setters
    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectId) {
        this.projectID = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    //setting parent object so that subproject can be added to the parent project

    public Project getParentProject() {
        return parentProject;
    }

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }
}