package main.java.org.example.tester;

import main.java.org.example.calculator.BigNumberCalculator;

import java.io.*;

public class BigNumberCalculatorValidator {

    private final String validationFilePath = "Test/src//sourceFiles/bigNumberAdditionTest.csv";
    private final int firstElementIndexInValidationFile = 0;
    private final int secondElementIndexInValidationFile = 1;
    private final int sumElementIndexInValidationFile = 2;

    public void validationTest(BigNumberCalculator calculator) throws FileNotFoundException {

        try (BufferedReader br = new BufferedReader(
                new FileReader(new File(this.validationFilePath))
        )) {
            String line;

            int testNO = 1;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                String firstNumber = values[this.firstElementIndexInValidationFile].trim();
                String secondNumber = values[this.secondElementIndexInValidationFile].trim();
                String actualResult = values[this.sumElementIndexInValidationFile].trim();
                String result = calculator.addWithChunks(firstNumber, secondNumber);

                if (result.equals(actualResult)) {
                    System.out.println("Test " + testNO + " passed");
                } else {
                    System.out.println("Test " + testNO + " not passed");
                    System.out.println(actualResult);
                    System.out.println(result);
                }
                System.out.println("----------------------------");
                testNO++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}