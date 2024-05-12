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
    private  List<SubProject> subprojects;

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

    public List<SubProject> getSubprojects(){
        return subprojects;
    }

    public void setSubprojects(List<SubProject> subprojects){
        this.subprojects = subprojects;
    }

    @Column(name = "projectName", unique = true, nullable = false)
    private String projectName;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @Column(name = "project_type")
    private String projectType;

    @ManyToOne
    @JoinColumn(name = "parent_projectid")
    private Project parentProject;

}