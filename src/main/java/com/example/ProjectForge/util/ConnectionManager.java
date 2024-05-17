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

    //Load application properties
    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = ConnectionManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            LOGGER.error("Failed to load application properties", e);
        }
        return properties;
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

    //Insert application properties into the database connection
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