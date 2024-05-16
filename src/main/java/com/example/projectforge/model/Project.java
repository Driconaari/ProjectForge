package com.example.projectforge.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectID;


    @OneToMany(mappedBy = "parentProject", cascade = CascadeType.ALL)
    private List<SubProject> subprojects;

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

    public List<SubProject> getSubprojects() {
        return subprojects;
    }

    public void setSubprojects(List<SubProject> subprojects) {
        this.subprojects = subprojects;
    }

    @Column(name = "projectName", unique = true, nullable = false)
    private String projectName;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;


    public List<SubProject> getSubProjects() {
        return this.subprojects;
    }
}