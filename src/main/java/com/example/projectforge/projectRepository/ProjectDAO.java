package com.example.projectforge.projectRepository;

import com.example.projectforge.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class ProjectDAO {
    private final DataSource dataSource;

    @Autowired
    public ProjectDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createProject(Project project) throws SQLException {
        String sql = "INSERT INTO Projects (project_name, description, deadline, parent_projectid) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, project.getProjectName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, project.getDeadline());
            statement.setInt(4, project.getParentProject() != null ? project.getParentProject().getProjectID() : null);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new project was inserted successfully!");
            }
        }
    }

    public Project findByProjectName(String projectName) throws SQLException {
        String sql = "SELECT * FROM Projects WHERE project_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, projectName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Project project = new Project();
                project.setProjectID(resultSet.getInt("projectID"));
                project.setProjectName(resultSet.getString("project_name"));
                project.setDescription(resultSet.getString("description"));
                project.setDeadline(resultSet.getDate("deadline"));
                project.setParentProject(getProjectById(resultSet.getInt("parent_projectid")));
                return project;
            }
        }
        return null;
    }

    private Project getProjectById(int parentProjectid) throws SQLException {
        String sql = "SELECT * FROM Projects WHERE projectID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, parentProjectid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Project project = new Project();
                project.setProjectID(resultSet.getInt("projectID"));
                project.setProjectName(resultSet.getString("project_name"));
                project.setDescription(resultSet.getString("description"));
                project.setDeadline(resultSet.getDate("deadline"));
                int parentId = resultSet.getInt("parent_projectid");
                if (!resultSet.wasNull()) {
                    project.setParentProject(getProjectById(parentId));
                }
                return project;
            }
        }
        return null;
    }

}