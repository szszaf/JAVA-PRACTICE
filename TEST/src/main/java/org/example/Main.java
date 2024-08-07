package main.java.org.example;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        BigNumberGenerator bigNumberGenerator = new BigNumberGenerator();
        bigNumberGenerator.generateToFile(50, 100,
                10000000, "Test/src//sourceFiles/bigNumberGen.csv");

        CalculatorPerformanceTester calculatorPerformanceTester =
                new CalculatorPerformanceTester();

        calculatorPerformanceTester.test();
    }
}