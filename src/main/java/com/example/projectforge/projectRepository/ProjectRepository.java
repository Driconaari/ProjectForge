package com.example.projectforge.projectRepository;

import com.example.projectforge.model.Project;
import com.example.projectforge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByUser(User user);
}
