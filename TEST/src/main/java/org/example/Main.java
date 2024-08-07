package main.java.org.example;

import main.java.org.example.generator.BigNumberGenerator;
import main.java.org.example.tester.CalculatorPerformanceTester;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args)  {
        BigNumberGenerator bigNumberGenerator = new BigNumberGenerator();
        bigNumberGenerator.generateToFile(50, 100,
                10000000, "Test/src//sourceFiles/bigNumberGen.csv");

        CalculatorPerformanceTester calculatorPerformanceTester =
                new CalculatorPerformanceTester();

        calculatorPerformanceTester.test();
    }
}