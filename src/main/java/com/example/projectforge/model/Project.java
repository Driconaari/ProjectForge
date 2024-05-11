package com.example.projectforge.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectID;

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }

    public String getProjectName() {
        return projectName;
    }

    public int getProjectID() {
        return projectID;
    }

    public String getDescription() {
        return description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getProjectType() {
        return projectType;
    }

    public Project getParentProject() {
        return parentProject;
    }

    @Column(name = "projectName", unique = true, nullable = false)
    private String projectName;

    private String description;

    private Date deadline;

    @Column(name = "project_type")
    private String projectType;

    @ManyToOne
    @JoinColumn(name = "parent_projectid")
    private Project parentProject;

    // Getters and setters...
}