package com.example.ProjectForge.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    //Insert application properties to get connection to database
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