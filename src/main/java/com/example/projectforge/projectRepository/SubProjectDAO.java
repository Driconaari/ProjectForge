package com.example.projectforge.projectRepository;

import com.example.projectforge.model.SubProject;
import jakarta.persistence.metamodel.SingularAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class SubProjectDAO implements SubProjectRepository {
    private final DataSource dataSource;
    private final ProjectDAO projectDAO;
    private static final Logger logger = LoggerFactory.getLogger(SubProjectDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SubProjectDAO(DataSource dataSource, ProjectDAO projectDAO) {
        this.dataSource = dataSource;
        this.projectDAO = projectDAO;
    }

    private SubProject createSubProjectFromResultSet(ResultSet resultSet) throws SQLException {
        SubProject subProject = new SubProject();
        subProject.setSubProjectID(resultSet.getInt("sub_projectid"));
        subProject.setSubProjectName(resultSet.getString("subProjectName"));
        int parentId = resultSet.getInt("parentProject");
        if (!resultSet.wasNull()) {
            subProject.setParentProject(projectDAO.findById(parentId).orElse(null));
        }
        return subProject;
    }

    @Override
    public SubProject saveSubProject(SubProject subProject) {
        String sql = "INSERT INTO subprojects (subProjectName, description, deadline, parentProject) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, subProject.getSubProjectName(), subProject.getDescription(), subProject.getDeadline(), subProject.getParentProject().getProjectID());
        return subProject;
    }

    @Override
    public Optional<SubProject> findById(SingularAttribute<AbstractPersistable, Serializable> id) {
        return Optional.empty();
    }

    @Override
    public Optional<SubProject> findById(int id) {
        String sql = "SELECT * FROM subprojects WHERE sub_projectid = ?";
        SubProject subProject = jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> createSubProjectFromResultSet(resultSet));
        return Optional.ofNullable(subProject);
    }

    @Override
    public List<SubProject> findAll() {
        String sql = "SELECT * FROM subprojects";
        return jdbcTemplate.query(sql, (resultSet, i) -> createSubProjectFromResultSet(resultSet));
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM subprojects WHERE sub_projectid = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public SubProject findBySubProjectName(String subProjectName) {
        String sql = "SELECT * FROM subprojects WHERE subProjectName = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{subProjectName}, (resultSet, i) -> createSubProjectFromResultSet(resultSet));
    }

    @Override
    public SubProject getSubProjectById(int subProjectId) throws SQLException {
        return null;
    }

    @Override
    public SubProject save(SubProject subproject) throws SQLException {
        return null;
    }

    @Override
    public List<SubProject> findByParentProjectId(int projectId) {
        String sql = "SELECT * FROM subprojects WHERE parentProject = ?";
        return jdbcTemplate.query(sql, new Object[]{projectId}, (resultSet, i) -> createSubProjectFromResultSet(resultSet));
    }
}