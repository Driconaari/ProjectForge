package com.example.projectforge.projectRepository;

import com.example.projectforge.model.SubProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SubProjectDAO implements SubProjectRepository {
    private final DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(SubProjectDAO.class);

    @Autowired
    public SubProjectDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void setPreparedStatementParameters(PreparedStatement statement, SubProject subProject) throws SQLException {
        statement.setString(1, subProject.getSubProjectName());
        statement.setString(2, subProject.getDescription());
        if (subProject.getDeadline() != null) {
            java.sql.Date sqlDate = new java.sql.Date(subProject.getDeadline().getTime());
            statement.setDate(3, sqlDate);
        } else {
            statement.setNull(3, Types.DATE);
        }
        if (subProject.getParentProject() != null) {
            statement.setInt(4, subProject.getParentProject().getProjectID());
        } else {
            statement.setNull(4, Types.INTEGER);
        }
    }

    private SubProject createSubProjectFromResultSet(ResultSet resultSet) throws SQLException {
        SubProject subProject = new SubProject();
        subProject.setSubProjectID(resultSet.getInt("subProjectID"));
        subProject.setSubProjectName(resultSet.getString("subProjectName"));
        int parentId = resultSet.getInt("parentProject");
        if (!resultSet.wasNull()) {
            ProjectDAO projectDAO = new ProjectDAO(dataSource);
            subProject.setParentProject(projectDAO.findById(parentId).orElse(null));
        }
        return subProject;
    }


   private SubProject insertSubProject(SubProject subProject) {
    String sql = "INSERT INTO SubProjects (subProjectName, description, deadline, parentProject) VALUES (?, ?, ?, ?)";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        setPreparedStatementParameters(statement, subProject);
        statement.executeUpdate();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                return findById(id).orElse(null);
            }
        }
    } catch (SQLException e) {
        logger.error("Error inserting subproject", e);
    }
    return null;
}

    @Override
    public SubProject saveSubProject(SubProject subProject) throws SQLException {
        return insertSubProject(subProject);
    }

    public SubProject save(SubProject subProject) {
        logger.info("Saving subproject: {}", subProject);
        SubProject savedSubProject = insertSubProject(subProject);
        logger.info("Saved subproject: {}", savedSubProject);
        return savedSubProject;
    }


@Override
public SubProject getSubProjectById(int subProjectId) throws SQLException {
    String sql = "SELECT * FROM SubProjects WHERE subProjectID = ?";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, subProjectId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return createSubProjectFromResultSet(resultSet);
        }
    } catch (SQLException e) {
        // Handle the exception
    }
    return null;
}

    @Override
    public Optional<SubProject> findById(int id) {
        String sql = "SELECT * FROM SubProjects WHERE subProjectID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                SubProject subProject = createSubProjectFromResultSet(resultSet);
                return Optional.of(subProject);
            }
        } catch (SQLException e) {
            // Handle the exception
        }
        return Optional.empty();
    }

    @Override
    public List<SubProject> findAll() {
        List<SubProject> subProjects = new ArrayList<>();
        String sql = "SELECT * FROM SubProjects";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                SubProject subProject = createSubProjectFromResultSet(resultSet);
                subProjects.add(subProject);
            }
        } catch (SQLException e) {
            // Handle the exception
        }

        return subProjects;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM SubProjects WHERE subProjectID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception
        }
    }

    @Override
    public SubProject findBySubProjectName(String subProjectName) throws SQLException {
        String sql = "SELECT * FROM SubProjects WHERE subProjectName = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subProjectName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createSubProjectFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            // Handle the exception
        }
        return null;
    }

}