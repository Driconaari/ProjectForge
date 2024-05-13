package com.example.projectforge.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "subprojects")
public class SubProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubProjectID") // specify the exact column name in the database
    private int sub_projectid;

    @ManyToOne
    @JoinColumn(name = "parentProject")
    private Project parentProject;

    private String subProjectName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    private String description;

    // Getters and setters...

    public Date getDeadline() {
        return deadline;
    }

    public String getDescription() {
        return description;
    }

    public int getSub_projectid() {
        return sub_projectid;
    }

    public Project getParentProject() {
        return parentProject;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectID(int sub_projectid) {
        this.sub_projectid = sub_projectid;
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