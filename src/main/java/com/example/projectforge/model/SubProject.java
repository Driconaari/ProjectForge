package com.example.projectforge.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("SubProject")
public class SubProject extends Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer subProjectId;

    @ManyToOne
    private Project parentProject;

    public Integer getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(Integer subProjectId) {
        this.subProjectId = subProjectId;
    }

    public Project getParentProject() {
        return parentProject;
    }

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }

private String subProjectName;

public String getSubProjectName() {
    return subProjectName;
}

public void setSubProjectName(String subProjectName) {
    this.subProjectName = subProjectName;
}

  public void setSubProjectID(int subprojectID) {
    this.subProjectId = subprojectID;
}
}