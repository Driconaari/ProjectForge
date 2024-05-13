package com.example.projectforge.model;

import com.example.projectforge.model.Project;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "subprojects")
public class SubProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subProjectID")
    private int subProjectID;

    @ManyToOne
    @JoinColumn(name = "parentProject")
    private Project parentProject;

    @Column(name = "subProjectName")
    private String subProjectName;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private Date deadline;

    // getters and setters...'

    public void setSubProjectID(int subProjectID) {
        this.subProjectID = subProjectID;
    }

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
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

    public String getDescription() {
        return description;
    }

    public Date getDeadline() {
        return deadline;
    }
}