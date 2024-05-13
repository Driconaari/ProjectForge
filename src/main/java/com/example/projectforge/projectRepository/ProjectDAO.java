package com.example.projectforge.projectRepository;

import com.example.projectforge.model.Project;
import com.example.projectforge.model.SubProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
public class ProjectDAO implements ProjectRepository {
    private final DataSource dataSource;

    @Autowired
    public ProjectDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveProject(Project project) throws SQLException {
        String sql = "INSERT INTO Projects (projectName, description, deadline, project_name) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, project.getProjectName());
            statement.setString(2, project.getDescription());
            if (project.getDeadline() != null) {
                java.sql.Date sqlDate = new java.sql.Date(project.getDeadline().getTime());
                statement.setDate(3, sqlDate);
            } else {
                statement.setNull(3, Types.DATE);
            }
            statement.setString(4, project.getProjectName());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new project was inserted successfully!");
            }
        }
    }

    public Project findByProjectName(String projectName) throws SQLException {
        String sql = "SELECT * FROM Projects WHERE projectName = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, projectName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Project project = new Project();
                project.setProjectID(resultSet.getInt("projectID"));
                project.setProjectName(resultSet.getString("projectName"));
                project.setDescription(resultSet.getString("description"));
                project.setDeadline(resultSet.getDate("deadline"));
                return project;
            }
        }
        return null;
    }

    @Override
    public Project getProjectById(int projectId) throws SQLException {
        String sql = "SELECT * FROM Projects WHERE projectID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Project project = new Project();
                project.setProjectID(resultSet.getInt("projectID"));
                project.setProjectName(resultSet.getString("projectName"));
                project.setDescription(resultSet.getString("description"));
                project.setDeadline(resultSet.getDate("deadline"));
                return project;
            }
        }
        return null;
    }

    @Override
    public Optional<Project> findById(int id) {
        String sql = "SELECT * FROM Projects WHERE projectID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Project project = new Project();
                project.setProjectID(resultSet.getInt("projectID"));
                project.setProjectName(resultSet.getString("projectName"));
                project.setDescription(resultSet.getString("description"));
                project.setDeadline(resultSet.getDate("deadline"));
                return Optional.of(project);
            }
        } catch (SQLException e) {
            // Handle the exception
        }
        return Optional.empty();
    }

    @Override
    public void save(Project project) {
        String sql = "INSERT INTO Projects (projectName, description, deadline) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, project.getProjectName());
            statement.setString(2, project.getDescription());
            if (project.getDeadline() != null) {
                java.sql.Date sqlDate = new java.sql.Date(project.getDeadline().getTime());
                statement.setDate(3, sqlDate);
            } else {
                statement.setNull(3, Types.DATE);
            }
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new project was inserted successfully!");
            }
        } catch (SQLException e) {
            // Handle the exception
        }
    }

    @Override
    public Iterable<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM Projects";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Project project = new Project();
                project.setProjectID(resultSet.getInt("projectID"));
                project.setProjectName(resultSet.getString("projectName")); // changed from project_name to projectName
                project.setDescription(resultSet.getString("description"));
                project.setDeadline(resultSet.getDate("deadline"));
                projects.add(project);
            }
        } catch (SQLException e) {
            // Handle the exception
        }

        return projects;
    }
}