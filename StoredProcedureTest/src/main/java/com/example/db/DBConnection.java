package com.example.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private Connection conn = null;
    private String username;
    private String password;
    private String dbName;
    public static final String dbServerAddress = "jdbc:postgresql://localhost:5432/";

    public DBConnection(String dbName, String username, String password) {
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    public boolean initConnection() {
        try {
            this.conn = DriverManager.
                    getConnection(dbServerAddress + this.dbName,
                            this.username, this.password);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean queryExecution(String query) {
        try(Statement stmt = this.conn.createStatement()) {
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
