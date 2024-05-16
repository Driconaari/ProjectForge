package com.example.projectforge.projectRepository;

import com.example.projectforge.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private Connection connection;

    public TaskDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Tasks");

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("TaskID"));
            task.setSubprojectId(resultSet.getInt("SubprojectID"));
            task.setTaskName(resultSet.getString("TaskName"));
            task.setDescription(resultSet.getString("Description"));
            task.setDuration(resultSet.getInt("Duration"));
            task.setResourceRequirement(resultSet.getString("ResourceRequirement"));
            task.setDeadline(resultSet.getDate("Deadline"));
            tasks.add(task);
        }

        return tasks;
    }

    // Additional methods for inserting, updating, and deleting tasks
    // ...
}
