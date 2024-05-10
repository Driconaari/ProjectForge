package com.example.projectforge.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("SubProject")
public class SubProject extends Project {

    @ManyToOne
    private Project parentProject;

    private String subProjectName;

    public Project getParentProject() {
        return parentProject;
    }

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }
}