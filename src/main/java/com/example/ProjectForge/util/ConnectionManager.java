package com.example.ProjectForge.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection conn = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

    // Load application properties
    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = ConnectionManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                LOGGER.error("Application properties file not found");
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load application properties", e);
        }
        return properties;
    }

    // Check if role_id exists in the database connection
    public boolean doesRoleIdExist(int roleId) {
        String SQL = "SELECT role_id FROM role WHERE role_id = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL)) {
            pstmt.setInt(1, roleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if the role_id exists and false if it does not
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking role_id existence", e);
            throw new RuntimeException(e);
        }
    }

    // Get database connection
    public static Connection getConnection() {
        if (conn != null) {
            return conn;
        }

        Properties properties = loadProperties();
        String dbUrl = properties.getProperty("spring.datasource.url");
        String uid = properties.getProperty("spring.datasource.username");
        String pwd = properties.getProperty("spring.datasource.password");

        try {
            conn = DriverManager.getConnection(dbUrl, uid, pwd);
        } catch (SQLException e) {
            LOGGER.error("Failed to connect to the database", e);
        }
        return conn;
    }
}
