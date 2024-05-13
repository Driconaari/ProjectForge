package com.example.projectforge.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
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

    @NotNull
    @Column(name = "subProjectName")
    private String subProjectName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)  // Ensure date is stored without time
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