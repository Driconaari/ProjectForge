package com.example.projectforge.repository;

import com.example.projectforge.model.Project;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepositoryImpl implements ProjectRepo {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Project create(Project project) {
        entityManager.persist(project);
        return project;
    }

    @Override
    public Project save(Project project) {
        return null;
    }

    @Override
    public Project findById(Long id) {
        return null;
    }

    @Override
    public List<Project> findAll() {
        return null;
    }

    @Override
    public void delete(Project project) {

    }
}