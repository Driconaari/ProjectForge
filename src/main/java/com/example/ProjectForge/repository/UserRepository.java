package com.example.ProjectForge.repository;

import com.example.ProjectForge.model.User;
import com.example.ProjectForge.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository implements IUserRepository {

    //Sign in with user'
    @Override
    public User login(String username, String password) {
        User user = null;

        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM user WHERE username = ? AND password = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int user_id = rs.getInt("user_id");
                user = new User(user_id, username, password);
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Sign up user
    @Override
    public void register(User user) {
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "INSERT INTO user (username ,password, role_id) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getRole_id());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int user_id = rs.getInt(1);
                user.setUser_id(user_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Is username taken
    @Override
    public boolean isUsernameTaken(String username) {
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT username FROM user WHERE username = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            // Returns true if the username is found, and false if it doesn't
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //edit user
    @Override
    public void editUser(User user, int user_id) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "UPDATE user SET username = ?, password = ? WHERE user_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getPassword());
                pstmt.setInt(3, user_id);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    //get user from id
    @Override
    public User getUserFromId(int user_id) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM user WHERE user_id = ?;";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            User user = null;

            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                user = new User(user_id, username, password);
            }
            return user;
        } catch (SQLException ex) {
            return null;
        }
    }

    //Get user id from project id
    @Override
    public int getUserID (int project_id){
        int user_id = 0;
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT user_id from project WHERE project_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user_id = rs.getInt("user_id");
            }
            return user_id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
