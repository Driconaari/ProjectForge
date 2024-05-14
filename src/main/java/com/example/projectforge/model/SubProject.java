package com.example.projectforge.model;


import jakarta.persistence.*;

@Entity
@Table(name = "SubProjects")
public class SubProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubProjectID")
    private int subProjectID;

    @Column(name = "SubProjectName")
    private String subProjectName;

    @Column(name = "Description")
    private String description;

    @Column(name = "Deadline")
    private String deadline;

    // Getters and Setters
    public int getSubProjectID() {
        return subProjectID;
    }

    public void setSubProjectID(int subProjectID) {
        this.subProjectID = subProjectID;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
