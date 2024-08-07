package main.java.org.example;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        BigNumberCalculator bigNumberCalculator = new BigNumberCalculator(10);

//        bigNumberCalculator.addWithChunks("1","2");

//        BigNumberCalculatorValidator calculatorValidator = new BigNumberCalculatorValidator();
//        calculatorValidator.validationTest(new BigNumberCalculator(10));

//        BigNumberGenerator bigNumberGenerator = new BigNumberGenerator();
//        bigNumberGenerator.generateToFile(50, 100,
//                10000000, "Test/src//sourceFiles/bigNumberGen.csv");
//
        CalculatorPerformanceTester performanceTester = new CalculatorPerformanceTester();
        System.out.println("Adding by positions performance");
        performanceTester.test(bigNumberCalculator::addByPositions);
        System.out.println("------------------------------------");
        System.out.println("Adding with chunks performance");
        performanceTester.test(bigNumberCalculator::addWithChunks);
        System.out.println("------------------------------------");
    }
}


class BigNumberGenerator {
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

        for(int i=0; i<numberOfDigits; i++) {
            number.insert(0, random.nextInt(this.decimalBase));
        }

        return number.toString();
    }
}


@FunctionalInterface
interface BigNumberCalculatorConsumer<T, V> {
    void accept(T t, V v);
}

/**
 *
 */
class CalculatorPerformanceTester {
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