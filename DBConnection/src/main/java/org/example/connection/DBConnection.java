package main.java.org.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.stream.Collectors;

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

    private boolean queryExecution(String query) {
        try(Statement stmt = this.conn.createStatement()) {
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createTable(String tableName, Map<String, String> tableFields) {
        String createQuery = tableFields.keySet().stream()
                .map(key -> key + " " + tableFields.get(key))
                .collect(Collectors.joining(",", "CREATE TABLE " + tableName + " (",")"));
        return queryExecution(createQuery);
    }

    public boolean setFieldNotNullable(String tableName, String fieldName) {
        String alterQuery = "ALTER TABLE " + tableName + " ALTER COLUMN " + fieldName + " SET NOT NULL;";
        return queryExecution(alterQuery);
    }

    public boolean setPrimaryKey(String tableName, String fieldName) {
        String alterQuery = "ALTER TABLE " + tableName +
                " ADD CONSTRAINT " + tableName + "_PK PRIMARY KEY" + " (" + fieldName + ")";
        return queryExecution(alterQuery);
    }

    public boolean setForeignKey(String tableName, String fieldName,
                                 String refTableName, String refFieldName) {
        String alterQuery = "ALTER TABLE " + tableName +
                "ADD CONSTRAINT " + tableName + "_FK FOREIGN KEY (" + fieldName + ")" + " REFERENCES " + refTableName + "(" + refFieldName + ")";
        return queryExecution(alterQuery);
    }

    public boolean insertOne(String tableName, RecordBuilder recordBuilder) {
        if (recordBuilder == null || !recordBuilder.isBuild()) return false;

        String insertQuery = "INSERT INTO " + tableName;

        return true;
    }

    private String getRecordString(Map<String, Object> fields) {
        return fields.keySet().stream().sorted()
                    .map(key -> fields.get(key).toString())
                    .collect(Collectors.joining(",","(",")"));
    }

}