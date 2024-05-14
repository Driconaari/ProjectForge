package com.example.projectforge.projectRepository;

import com.example.projectforge.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
