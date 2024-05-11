package com.example.projectforge.model;

import jakarta.persistence.*;

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

    // Getters and setters...


    public void setSubProjectID(int subProjectID) {
        this.subProjectID = subProjectID;
    }

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
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
}