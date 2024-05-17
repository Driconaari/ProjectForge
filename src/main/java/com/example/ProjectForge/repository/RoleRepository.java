package com.example.ProjectForge.repository;

import com.example.ProjectForge.model.Role;
import com.example.ProjectForge.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleRepository implements IRoleRepository {

    //Get all roles
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM role";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int role_id = rs.getInt("role_id");
                String role_name = rs.getString("role_name");
                Role role = new Role();
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roles;
    }

    //Check if role_id exists in the database connection
    public boolean doesRoleIdExist(int roleId) {
    try {
        Connection con = ConnectionManager.getConnection();
        String SQL = "SELECT role_id FROM role WHERE role_id = ?";
        PreparedStatement pstmt = con.prepareStatement(SQL);
        pstmt.setInt(1, roleId);
        ResultSet rs = pstmt.executeQuery();

        // Returns true if the role_id exists and false if it does not
        return rs.next();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
}
