package com.example.projectforge.projectRepository;

import com.example.projectforge.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public Task save(Task task) throws SQLException {
        String sql = "INSERT INTO tasks (taskId, subprojectId, taskName, description, duration, resourceRequirement, deadline, parentProjectId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = new Object[]{task.getTaskId(), task.getSubprojectId(), task.getTaskName(), task.getDescription(), task.getDuration(), task.getResourceRequirement(), task.getDeadline(), task.getParentProject().getProjectID()};
        jdbcTemplate.update(sql, params);
        return task;
    }

    public Optional<Task> findById(int id) {
        String sql = "SELECT * FROM tasks WHERE taskId = :taskId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("taskId", id);
        RowMapper<Task> rowMapper = (rs, rowNum) -> {
            Task task = new Task();
            task.setTaskId(rs.getInt("taskId"));
            // set other fields...
            return task;
        };
        Task task = namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper);
        return Optional.ofNullable(task);
    }

    public List<Task> findAll() {
        String sql = "SELECT * FROM tasks";
        RowMapper<Task> rowMapper = (rs, rowNum) -> {
            Task task = new Task();
            task.setTaskId(rs.getInt("taskId"));
            // set other fields...
            return task;
        };
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM tasks WHERE taskId = :taskId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("taskId", id);
        jdbcTemplate.update(sql, params);
    }
}