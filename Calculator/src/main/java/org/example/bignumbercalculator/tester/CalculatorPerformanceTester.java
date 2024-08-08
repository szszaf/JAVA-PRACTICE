package org.example.bignumbercalculator.tester;



import org.example.bignumbercalculator.calculator.BigNumberCalculator;
import org.example.bignumbercalculator.interfaces.BigNumberCalculatorConsumer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CalculatorPerformanceTester {
    private final String testSetFilePath = "Test/src/sourceFiles/bigNumberGen.csv";
    private final BigNumberCalculator calculator = new BigNumberCalculator();

    public void testFunction(BigNumberCalculatorConsumer<String, String> functionToTest) {
        long startTime = System.currentTimeMillis();

        try (BufferedReader br = new BufferedReader(
                new FileReader(this.testSetFilePath)
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