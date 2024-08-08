package main.java.org.example.bignumbercalculator.generator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BigNumberGenerator {
    private final String defaultSaveFilePath = "Test/src//sourceFiles/bigNumberAdditionTest.csv";
    private final int decimalBase = 10;

    public boolean generateToFile(int minDigits, int maxDigits,
                                  int numberOfRandoms, String filePath) {

        try (FileWriter writer = new FileWriter(filePath)) {
            for (int i = 0; i < numberOfRandoms; i++) {
                String number1 = generateRandomNumber(minDigits, maxDigits);
                String number2 = generateRandomNumber(minDigits, maxDigits);
                writer.append(number1).append(";").append(number2).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private String generateRandomNumber(int minDigits, int maxDigits) {
        Random random = new Random();
        int numberOfDigits = random.nextInt(maxDigits - minDigits + 1) + minDigits;
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < numberOfDigits; i++) {
            number.insert(0, random.nextInt(this.decimalBase));
        }

        return number.toString();
    }
}