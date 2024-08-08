package main.java.org.example.bignumbercalculator;

import main.java.org.example.bignumbercalculator.generator.BigNumberGenerator;
import main.java.org.example.bignumbercalculator.tester.CalculatorPerformanceTester;

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