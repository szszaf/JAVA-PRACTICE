package main.java.org.example;

import main.java.org.example.interfaces.BigNumberCalculatorConsumer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CalculatorPerformanceTester {
    private BigNumberCalculator calculator = new BigNumberCalculator();
    private final String testSetFilePath = "Test/src/sourceFiles/bigNumberGen.csv";

    public void testFunction(BigNumberCalculatorConsumer<String, String> functionToTest) {
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

    public void test() {
        BigNumberCalculator bigNumberCalculator = new BigNumberCalculator(10);
        CalculatorPerformanceTester performanceTester = new CalculatorPerformanceTester();
        System.out.println("Adding by positions performance");
        performanceTester.testFunction(bigNumberCalculator::addByPositions);
        System.out.println("------------------------------------");
        System.out.println("Adding with chunks performance");
        performanceTester.testFunction(bigNumberCalculator::addWithChunks);
        System.out.println("------------------------------------");
    }
}