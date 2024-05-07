package com.example.projectforge.model;

import jakarta.persistence.*;

@Entity
@Table(name = "resources")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ResourceID")
    private int resourceID;

    @Column(name = "ResourceName")
    private String resourceName;

    @Column(name = "CompetencyLevel")
    private String competencyLevel;

    // Constructors
    public Resource() {
        // Default constructor
    }

    public Resource(int resourceID, String resourceName, String competencyLevel) {
        this.resourceID = resourceID;
        this.resourceName = resourceName;
        this.competencyLevel = competencyLevel;
    }

    // Getters and Setters
    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getCompetencyLevel() {
        return competencyLevel;
    }

    public void setCompetencyLevel(String competencyLevel) {
        this.competencyLevel = competencyLevel;
    }
}