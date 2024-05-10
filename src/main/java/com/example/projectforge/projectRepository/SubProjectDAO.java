package com.example.projectforge.projectRepository;

import com.example.projectforge.model.Project;
import com.example.projectforge.model.SubProject;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubProjectDAO implements SubProjectRepository {
    private Connection connection;

    public SubProjectDAO(String dbUrl, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(dbUrl, user, password);
    }

    @Override
    public void createSubProject(SubProject subProject) throws SQLException {
        String sql = "INSERT INTO subprojects (SubprojectName, Description, Deadline, ProjectID) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, subProject.getSubProjectName());
        statement.setString(2, subProject.getDescription());
        statement.setDate(3, subProject.getDeadline());
        statement.setInt(4, subProject.getParentProject() != null ? subProject.getParentProject().getProjectID() : null);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new subproject was inserted successfully!");
        }
    }

    @Override
    public SubProject findBySubProjectName(String subProjectName) throws SQLException {
        String sql = "SELECT * FROM subprojects WHERE SubprojectName = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, subProjectName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            SubProject subProject = new SubProject();
            subProject.setSubProjectID(resultSet.getInt("SubprojectID"));
            subProject.setSubProjectName(resultSet.getString("SubprojectName"));
            subProject.setDescription(resultSet.getString("Description"));
            subProject.setDeadline(resultSet.getDate("Deadline"));
            // You'll need to implement a method to fetch the parent project by its ID
            subProject.setParentProject(getProjectById(resultSet.getInt("ProjectID")));
            return subProject;
        }
        return null;
    }

    @Override
    public SubProject getSubProjectById(int subProjectId) throws SQLException {
        String sql = "SELECT * FROM subprojects WHERE SubprojectID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, subProjectId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            SubProject subProject = new SubProject();
            subProject.setSubProjectID(resultSet.getInt("SubprojectID"));
            subProject.setSubProjectName(resultSet.getString("SubprojectName"));
            subProject.setDescription(resultSet.getString("Description"));
            subProject.setDeadline(resultSet.getDate("Deadline"));
            // Since the ProjectID column could be null, check before trying to fetch the parent project
            int parentId = resultSet.getInt("ProjectID");
            if (!resultSet.wasNull()) {
                subProject.setParentProject(getProjectById(parentId));
            }
            return subProject;
        }
        return null;
    }

private Project getProjectById(int parentId) throws SQLException {
    String sql = "SELECT * FROM Projects WHERE projectID = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, parentId);
    ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
        Project project = new Project();
        project.setProjectID(resultSet.getInt("projectID"));
        project.setProjectName(resultSet.getString("project_name"));
        project.setDescription(resultSet.getString("description"));
        project.setDeadline(resultSet.getDate("deadline"));
        return project;
    }
    return null;
}

public List<SubProject> findAll() throws SQLException {
    List<SubProject> subProjects = new ArrayList<>();
    String sql = "SELECT * FROM subprojects";
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(sql);
    while (resultSet.next()) {
        SubProject subProject = new SubProject();
        subProject.setSubProjectID(resultSet.getInt("SubprojectID"));
        subProject.setSubProjectName(resultSet.getString("SubprojectName"));
        subProject.setDescription(resultSet.getString("Description"));
        subProject.setDeadline(resultSet.getDate("Deadline"));
        int parentId = resultSet.getInt("ProjectID");
        if (!resultSet.wasNull()) {
            subProject.setParentProject(getProjectById(parentId));
        }
        subProjects.add(subProject);
    }
    return subProjects;
}

public SubProject save(SubProject subProject) throws SQLException {
    if (subProject.getSubProjectId() == null) {
        createSubProject(subProject);
    } else {
        updateSubProject(subProject);
    }
    return subProject;
}

private void updateSubProject(SubProject subProject) throws SQLException {
    String sql = "UPDATE subprojects SET SubprojectName = ?, Description = ?, Deadline = ?, ProjectID = ? WHERE SubprojectID = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setString(1, subProject.getSubProjectName());
    statement.setString(2, subProject.getDescription());
    statement.setDate(3, subProject.getDeadline());
    statement.setInt(4, subProject.getParentProject() != null ? subProject.getParentProject().getProjectID() : null);
    statement.setInt(5, subProject.getSubProjectId());
    statement.executeUpdate();
}

}