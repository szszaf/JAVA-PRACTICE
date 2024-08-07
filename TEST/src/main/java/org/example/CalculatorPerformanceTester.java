package main.java.org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CalculatorPerformanceTester {
    private BigNumberCalculator calculator = new BigNumberCalculator();
    private final String testSetFilePath = "Test/src/sourceFiles/bigNumberGen.csv";

    public void test(BigNumberCalculatorConsumer<String, String> functionToTest) {
        long startTime = System.currentTimeMillis();

        try (BufferedReader br = new BufferedReader(
                new FileReader(new File(this.testSetFilePath))
        )) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                functionToTest.accept(values[0], values[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;

        System.out.println("Duration time: " + duration + "s");
    }
}