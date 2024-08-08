package main.java.org.example.connection;

public enum TypesDB {
    SMALLINT("SMALLINT"),
    INTEGER("INTEGER"),
    BIGINT("BIGINT"),
    DECIMAL("DECIMAL"),
    NUMERIC("NUMERIC"),
    REAL("REAL"),
    MONEY("MONEY"),
    CHAR("CHAR"),
    VARCHAR("VARCHAR"),
    TEXT("TEXT"),
    DATE("DATE"),
    TIME("TIME"),
    TIMESTAMP("TIMESTAMP"),
    BOOLEAN("BOOLEAN");

    private String sqlType;

    TypesDB(String sqlType) {
        this.sqlType = sqlType;
    }

}
