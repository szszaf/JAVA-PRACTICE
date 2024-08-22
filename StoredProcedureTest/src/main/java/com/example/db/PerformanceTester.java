package com.example.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.stream.IntStream;

public class PerformanceTester {
    private final DBConnection connection;

    public PerformanceTester() {
        Dotenv dotenv = Dotenv.configure()
                .directory("C:\\Users\\Szymon\\IdeaProjects\\JAVA-PRACTICE\\StoredProcedureTest")
                .load();

        this.connection = new DBConnection(
                dotenv.get("DB_NAME"),
                dotenv.get("DB_USER"),
                dotenv.get("DB_PASSWORD"));
        this.connection.initConnection();
    }

    public void test(int recordsCount, int numberOfRepeats) {
        this.connection.deleteFromOriginals();
        this.connection.deleteFromSecondaries();
        this.connection.generateAndSave(recordsCount);

        Long programmaticallyStart = System.currentTimeMillis();
        IntStream.range(0, numberOfRepeats).forEach(i ->
                {
                    this.connection.copySumProductProgrammatically();
                });
        Long programmaticallyEnd = System.currentTimeMillis();

        System.out.printf("Programmatically: %.2fs\n", (programmaticallyEnd - programmaticallyStart) / 1000.0);

        Long storedProcedureStart = System.currentTimeMillis();
        IntStream.range(0, numberOfRepeats).forEach(i ->
        {
            this.connection.copySumProductStoredProcedure();
        });
        Long storedProcedureEnd = System.currentTimeMillis();

        System.out.printf("Stored Procedure: %.2fs\n", (storedProcedureEnd - storedProcedureStart) / 1000.0);
    }




}
