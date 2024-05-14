package com.example.projectforge.userRepository;

import com.example.projectforge.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    //In this code, JdbcTemplate is used to execute SQL queries.
    // The RowMapper is used to map the result of a query to a User object.
    // You'll need to implement the rest of the methods in a similar way.
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getLong("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    };

@Override
public User save(User user) {
    String sql = "INSERT INTO user (username, password, email, role_id) VALUES (?, ?, ?, ?)";
    try {
        // Set role_id as "ROLE_USER" as standard
        String roleId = "ROLE_USER";
        int rowsAffected = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), roleId);
        if (rowsAffected > 0) {
            System.out.println("User saved: " + user.getUsername());
        } else {
            System.out.println("No rows affected. User was not saved.");
        }
    } catch (Exception e) {
        System.out.println("Error saving user: " + e.getMessage());
    }
    return user;
}

    @Override
    public User findById(String id) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void delete(User user) {
        String sql = "DELETE FROM user WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getUserId());
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, rowMapper);
    }

    @Override
    public void register(User newUser) {
        save(newUser);
    }

    @Override
    public User login(String username, String password) {
        // Implement login logic here
        return null;
    }

    @Override
    public boolean isUsernameTaken(String username) {
        // Implement username check logic here
        return false;
    }

    @Override
    public int getUserID(int projectId) {
        // Implement user ID retrieval logic here
        return 0;
    }

    @Override
    public void editUser(User user, long userId) {
        // Implement user editing logic here
    }

    @Override
    public User getUserFromId(long userId) {
        // Implement user retrieval by ID logic here
        return null;
    }

    @Override
public String findRoleNameByRoleId(String roleId) {
    String sql = "SELECT role_name FROM role WHERE role_id = ?";
    try {
        return jdbcTemplate.queryForObject(sql, new Object[]{roleId}, String.class);
    } catch (EmptyResultDataAccessException e) {
        return null;
    }
}
}