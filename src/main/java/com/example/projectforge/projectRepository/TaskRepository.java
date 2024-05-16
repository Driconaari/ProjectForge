package com.example.projectforge.projectRepository;

import com.example.projectforge.model.Task;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository {

    Task save(Task task) throws SQLException;

    Optional<Task> findById(int id);

    List<Task> findAll();

    void deleteById(int id);

}