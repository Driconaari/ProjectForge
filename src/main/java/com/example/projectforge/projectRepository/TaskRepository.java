package com.example.projectforge.projectRepository;

import com.example.projectforge.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Task save(Task task) throws SQLException {
        // Implement save operation using jdbcTemplate
        // jdbcTemplate.update(SQL, params);
        return task;
    }

    public Optional<Task> findById(int id) {
        // Implement find operation using jdbcTemplate
        // jdbcTemplate.queryForObject(SQL, params, rowMapper);
        return Optional.empty();
    }

    public List<Task> findAll() {
        // Implement find all operation using jdbcTemplate
        // jdbcTemplate.query(SQL, rowMapper);
        return List.of();
    }

    public void deleteById(int id) {
        // Implement delete operation using jdbcTemplate
        // jdbcTemplate.update(SQL, params);
    }
}