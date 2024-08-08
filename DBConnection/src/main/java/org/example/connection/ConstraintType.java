package main.java.org.example.connection;

public enum ConstraintType {
    PRIMARY_KEY("PRIMARY KEY"),
    FOREIGN_KEY("FOREIGN KEY"),
    UNIQUE("UNIQUE"),
    NOT_NULL("NOT NULL");

    private String name;

    ConstraintType(String name) {
        this.name = name;
    }
}
