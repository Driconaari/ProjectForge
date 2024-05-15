package com.example.projectforge.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static Connection conn = null;

    //Load application properties
    private static String getProperty(String property_name) {
        Properties properties = new Properties();
        try (InputStream input = ConnectionManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);

        } catch (IOException e) {
            System.out.println("Failed to load application properties");
            e.printStackTrace();
        }
        return properties.getProperty(property_name);
    }

    //Insert application properties to get connection to database
    public static Connection getConnection() {
        if (conn != null) {
            return conn;
        }

        String dbUrl = getProperty("spring.datasource.url");
        String uid = getProperty("spring.datasource.username");
        String pwd = getProperty("spring.datasource.password");

        try {
            conn = DriverManager.getConnection(dbUrl, uid, pwd);
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database");
            e.printStackTrace();
        }
        return conn;
    }
}