package com.example.projectforge.projectRepository;

import com.example.projectforge.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectDAO implements ProjectRepository {
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
        if (project.getDeadline() != null) {
            java.sql.Date sqlDate = new java.sql.Date(project.getDeadline().getTime());
            statement.setDate(3, sqlDate);
        }else {
            statement.setObject(4, null);
        }
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

    public Project getProjectById(int parentProjectid) throws SQLException {
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
                project.setProjectName(resultSet.getString("project_name"));
                project.setDescription(resultSet.getString("description"));
                project.setDeadline(resultSet.getDate("deadline"));
                int parentId = resultSet.getInt("parent_projectid");
                if (!resultSet.wasNull()) {
                    project.setParentProject(getProjectById(parentId));
                }
                return Optional.of(project);
            }
        } catch (SQLException e) {
            // Handle the exception
        }
        return Optional.empty();
    }

 @Override
public void save(Project project) {
    String sql = project.getProjectID() > 0 ?
            "UPDATE Projects SET project_name = ?, description = ?, deadline = ?, parent_projectid = ? WHERE projectID = ?" :
            "INSERT INTO Projects (project_name, description, deadline, parent_projectid) VALUES (?, ?, ?, ?)";

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
        if (project.getParentProject() != null) {
            statement.setInt(4, project.getParentProject().getProjectID());
        } else {
            statement.setNull(4, Types.INTEGER);
        }
        if (project.getProjectID() > 0) {
            statement.setInt(5, project.getProjectID());
        }
        statement.executeUpdate();
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
                project.setProjectName(resultSet.getString("project_name"));
                project.setDescription(resultSet.getString("description"));
                project.setDeadline(resultSet.getDate("deadline"));
                int parentId = resultSet.getInt("parent_projectid");
                if (!resultSet.wasNull()) {
                    project.setParentProject(getProjectById(parentId));
                }
                projects.add(project);
            }
        } catch (SQLException e) {
            // Handle the exception
        }

        return projects;
    }
}