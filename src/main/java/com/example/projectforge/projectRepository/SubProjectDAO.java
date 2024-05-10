package com.example.projectforge.projectRepository;

import com.example.projectforge.model.Project;
import com.example.projectforge.model.SubProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubProjectDAO implements SubProjectRepository {
    private final DataSource dataSource;

    @Autowired
    public SubProjectDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

@Override
public void addSubProject(SubProject subProject) throws SQLException {
    String sql = "INSERT INTO subprojects (SubprojectID, SubprojectName, Description, Deadline, ProjectID) VALUES (?, ?, ?, ?, ?)";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, subProject.getSubProjectID());
        statement.setString(2, subProject.getSubProjectName());
        statement.setString(3, subProject.getDescription());
        if (subProject.getDeadline() != null) {
            statement.setDate(4, subProject.getDeadline());
        } else {
            statement.setNull(4, Types.DATE);
        }
        if (subProject.getParentProject() != null) {
            statement.setInt(5, subProject.getParentProject().getProjectID());
        } else {
            statement.setNull(5, Types.INTEGER);
        }
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new subproject was inserted successfully!");
        }
    }
}

    @Override
    public SubProject findBySubProjectName(String subProjectName) throws SQLException {
        String sql = "SELECT * FROM subprojects WHERE SubprojectName = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subProjectName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                SubProject subProject = new SubProject();
                subProject.setProjectID(resultSet.getInt("SubprojectID"));
                subProject.setSubProjectName(resultSet.getString("SubprojectName"));
                subProject.setDescription(resultSet.getString("Description"));
                subProject.setDeadline(resultSet.getDate("Deadline"));
                subProject.setParentProject(getProjectById(resultSet.getInt("ProjectID")));
                return subProject;
            }
        }
        return null;
    }

    @Override
    public SubProject getSubProjectById(int subProjectId) throws SQLException {
        String sql = "SELECT * FROM subprojects WHERE SubprojectID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, subProjectId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                SubProject subProject = new SubProject();
                subProject.setProjectID(resultSet.getInt("SubprojectID"));
                subProject.setSubProjectName(resultSet.getString("SubprojectName"));
                subProject.setDescription(resultSet.getString("Description"));
                subProject.setDeadline(resultSet.getDate("Deadline"));
                int parentId = resultSet.getInt("ProjectID");
                if (!resultSet.wasNull()) {
                    subProject.setParentProject(getProjectById(parentId));
                }
                return subProject;
            }
        }
        return null;
    }

    private Project getProjectById(int parentId) throws SQLException {
        String sql = "SELECT * FROM Projects WHERE projectID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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
        }
        return null;
    }

  @Override
public List<SubProject> findAll() {
    List<SubProject> subProjects = new ArrayList<>();
    String sql = "SELECT * FROM subprojects";
    try (Connection connection = dataSource.getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
        while (resultSet.next()) {
            SubProject subProject = new SubProject();
            subProject.setProjectID(resultSet.getInt("SubprojectID"));
            subProject.setSubProjectName(resultSet.getString("SubprojectName"));
            subProject.setDescription(resultSet.getString("Description"));
            subProject.setDeadline(resultSet.getDate("Deadline"));
            int parentId = resultSet.getInt("ProjectID");
            if (!resultSet.wasNull()) {
                subProject.setParentProject(getProjectById(parentId));
            }
            subProjects.add(subProject);
        }
    } catch (SQLException e) {
        // Handle the SQLException
        e.printStackTrace();
    }
    return subProjects;
}

@Override
public SubProject save(SubProject subProject) {
    try {
        if (subProject.getProjectID() == 0) {
            addSubProject(subProject);
        } else {
            updateSubProject(subProject);
        }
    } catch (SQLException e) {
        // Handle the SQLException
        e.printStackTrace();
    }
    return subProject;

}

   private void updateSubProject(SubProject subProject) throws SQLException {
    String sql = "UPDATE subprojects SET SubprojectName = ?, Description = ?, Deadline = ?, ProjectID = ? WHERE SubprojectID = ?";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, subProject.getSubProjectName());
        statement.setString(2, subProject.getDescription());
        statement.setDate(3, subProject.getDeadline());
        if (subProject.getParentProject() != null) {
            statement.setInt(4, subProject.getParentProject().getProjectID());
        } else {
            statement.setNull(4, Types.INTEGER);
        }
        statement.setInt(5, subProject.getProjectID());
        statement.executeUpdate();
    }
}
}