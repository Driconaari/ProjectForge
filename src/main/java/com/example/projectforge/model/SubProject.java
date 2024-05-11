package com.example.projectforge.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "SubProjects")
public class SubProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subProjectID;

    @ManyToOne
    @JoinColumn(name = "parentProject")
    private Project parentProject;

    private String subProjectName;
    private Date deadline;
    private String description;

    // Getters and setters...

    public Date getDeadline() {
        return deadline;
    }

    public String getDescription() {
        return description;
    }

    public int getSubProjectID() {
        return subProjectID;
    }

    public Project getParentProject() {
        return parentProject;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectID(int subProjectID) {
        this.subProjectID = subProjectID;
    }

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}