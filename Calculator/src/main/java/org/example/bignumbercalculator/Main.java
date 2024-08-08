package org.example.bignumbercalculator;

import org.example.bignumbercalculator.generator.BigNumberGenerator;
import org.example.bignumbercalculator.tester.CalculatorPerformanceTester;

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