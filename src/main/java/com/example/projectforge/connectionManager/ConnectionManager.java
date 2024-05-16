package com.example.projectforge.connectionManager;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionManager {
    private static ConnectionManager instance = null;
    private String USERNAME;
    private String PASSWORD;
    private String CONN_STRING;

    @Autowired
    private Environment env;

    private Connection connection = null;

    private ConnectionManager() {
    }

    @PostConstruct
    public void init() {
        USERNAME = env.getProperty("spring.datasource.username");
        PASSWORD = env.getProperty("spring.datasource.password");
        CONN_STRING = env.getProperty("spring.datasource.url");
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    private boolean openConnection() {
        try {
            connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection() {
        if (connection == null) {
            if (openConnection()) {
                System.out.println("Connection opened");
                return connection;
            } else {
                return null;
            }
        }
        return connection;
    }

    public void close() {
        System.out.println("Closing connection");
        try {
            connection.close();
            connection = null;
        } catch (Exception e) {
        }
    }
}