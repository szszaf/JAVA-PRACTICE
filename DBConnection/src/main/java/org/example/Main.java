package main.java.org.example;

import main.java.org.example.connection.ConstraintType;
import main.java.org.example.connection.DBConnection;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBConnection dbConnection = new DBConnection("TestDB", "postgres", "password");
        dbConnection.initConnection();
//        Map<String, String> fields = new HashMap<String, String>();
//        fields.put("PK_EXAMPLE", "int");
//        dbConnection.createTable("Example",fields);

        //dbConnection.setFieldNotNullable("Example", "PK_EXAMPLE");
        //dbConnection.addConstraintTable("Example", "PK_EXAMPLE", ConstraintType.PRIMARY_KEY);
    }
}