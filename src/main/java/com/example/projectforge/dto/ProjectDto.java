package com.example.projectforge.dto;

import java.util.Date;

public class ProjectDto {
    private int projectID;
    private String projectName;
    private String description;
    private Date deadline;




    public int getProjectID() {
        return projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDescription() {
        return description;
    }

    public java.sql.Date getDeadline() {
        return (java.sql.Date) deadline;
    }
}
