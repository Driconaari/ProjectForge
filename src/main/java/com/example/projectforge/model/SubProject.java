package com.example.projectforge.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "subprojects")
public class SubProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_projectid")
    private int subProjectID;

    @Column(name = "subProjectName")
    private String subProjectName;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @ManyToOne
    @JoinColumn(name = "parentProject")
    private Project parentProject;

    // getters and setters...

    public void setSubProjectID(int subProjectID) {
        this.subProjectID = subProjectID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public int getSubProjectID() {
        return subProjectID;
    }

    public String getDescription() {
        return description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Project getParentProject() {
        return parentProject;
    }

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }

    public String getSubProjectName() {
        return subProjectName;
    }
}