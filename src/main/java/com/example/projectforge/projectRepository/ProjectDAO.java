package com.example.projectforge.projectRepository;

import com.example.projectforge.model.Project;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ProjectDAO {
    private Connection connection;

    public ProjectDAO(String dbUrl, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(dbUrl, user, password);
    }

    public void createProject(Project project) throws SQLException {
        String sql = "INSERT INTO Projects (project_name, description, deadline, parent_projectid) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, project.getProjectName());
        statement.setString(2, project.getDescription());
        statement.setDate(3, project.getDeadline());
        statement.setInt(4, project.getParentProject() != null ? project.getParentProject().getProjectID() : null);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new project was inserted successfully!");
        }
    }

    public Project findByProjectName(String projectName) throws SQLException {
        String sql = "SELECT * FROM Projects WHERE project_name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, projectName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Project project = new Project();
            project.setProjectID(resultSet.getInt("projectID"));
            project.setProjectName(resultSet.getString("project_name"));
            project.setDescription(resultSet.getString("description"));
            project.setDeadline(resultSet.getDate("deadline"));
            // You'll need to implement a method to fetch the parent project by its ID
            project.setParentProject(getProjectById(resultSet.getInt("parent_projectid")));
            return project;
        }
        return null;
    }

  private Project getProjectById(int parentProjectid) throws SQLException {
    String sql = "SELECT * FROM Projects WHERE projectID = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, parentProjectid);
    ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
        Project project = new Project();
        project.setProjectID(resultSet.getInt("projectID"));
        project.setProjectName(resultSet.getString("project_name"));
        project.setDescription(resultSet.getString("description"));
        project.setDeadline(resultSet.getDate("deadline"));
        // Since the parent_projectid column could be null, check before trying to fetch the parent project
        int parentId = resultSet.getInt("parent_projectid");
        if (!resultSet.wasNull()) {
            project.setParentProject(getProjectById(parentId));
        }
        return project;
    }
    return null;
}

    // Similarly, you can implement update, and delete methods
}