package org.example;

import javax.print.DocFlavor;
import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        BigNumberCalculator bigNumberCalculator = new BigNumberCalculator(2);
//        System.out.println(bigNumberCalculator.add2("102313133", "77"));
//        System.out.println(bigNumberCalculator.add2(
//                new String(new char[30]).replace("\0", "1"),
//                new String(new char[30]).replace("\0", "9")
//        ));

//        System.out.println(bigNumberCalculator.getCurrentChunk("1234567890", 6));
//        System.out.println(bigNumberCalculator.getChunkSliecedNumber("1234567890", 6));

        //System.out.println(bigNumberCalculator.add2("100000","10000"));

//        BigNumberCalculatorValidator calculatorValidator = new BigNumberCalculatorValidator();
//        calculatorValidator.validationTest(new BigNumberCalculator(4));

//        BigNumberGenerator bigNumberGenerator = new BigNumberGenerator();
//        bigNumberGenerator.generateToFile(5, 50,
//                1000, "Test/src//sourceFiles/bigNumberGen.csv");

        CalculatorPerformanceTester performanceTester = new CalculatorPerformanceTester();
        performanceTester.test(new BigNumberCalculator(4)::add2);
    }
}


class BigNumberCalculator {

    private final int decimalBase = 10;
    private int chunkSize = 8;

    public BigNumberCalculator() { }
    public BigNumberCalculator(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public String add(String firstNumber, String secondNumber) {
        Map<Integer, Integer> firstNumberPositionMap = getDigitsPositions(firstNumber);
        Map<Integer, Integer> secondNumberPositionMap = getDigitsPositions(secondNumber);

        int numberMaxLength = firstNumberPositionMap.size() > secondNumberPositionMap.size() ?
                firstNumberPositionMap.size() : secondNumberPositionMap.size();

        Map<Integer, Integer> result = new TreeMap<>();
        int carrying = 0;

        for (int i=0; i < numberMaxLength; i++) {
            int resultFromCurrentPositions =
                    (firstNumberPositionMap.getOrDefault(i, 0) +
                            secondNumberPositionMap.getOrDefault(i, 0) + carrying);
            int resultOnCurrentPosition = resultFromCurrentPositions % decimalBase;
            result.put(i, resultOnCurrentPosition);
            carrying = resultFromCurrentPositions >= decimalBase ? 1 : 0;
        }

        if (carrying != 0) {
            result.put(numberMaxLength, carrying);
        }

        StringBuilder resultStringBuilder = new StringBuilder();
        result.values().stream().forEach(resultStringBuilder::append);
        resultStringBuilder.reverse();
        return resultStringBuilder.toString();
    }


    public String add2(String firstNumber, String secondNumber) {

        StringBuilder resultStringBuilder = new StringBuilder();
        int carrying = 0;

        while (!firstNumber.isEmpty() && !secondNumber.isEmpty()) {
            String firstStringCurrentChunk = getCurrentChunk(firstNumber);
            String secondStringCurrentChunk = getCurrentChunk(secondNumber);

            firstNumber = getChunkSliecedNumber(firstNumber);
            secondNumber = getChunkSliecedNumber(secondNumber);

            int currentChunkSum = getNumberFromString(firstStringCurrentChunk) +
                    getNumberFromString(secondStringCurrentChunk) + carrying;

            resultStringBuilder.insert(0, currentChunkSum);

            if(getNumberOfDigits(currentChunkSum) > this.chunkSize) {
                carrying = getOldestDigit(currentChunkSum);
                resultStringBuilder.deleteCharAt(0);
            } else if(getNumberOfDigits(currentChunkSum) < this.chunkSize) {
                int digitsDifference = this.chunkSize - getNumberOfDigits(currentChunkSum);
                resultStringBuilder.insert(0, "0".repeat(digitsDifference - 1));
                carrying = 0;
            }   else {
                carrying = 0;
            }
        }


        String longerStringRest = firstNumber.length() != 0 ? firstNumber :
                (secondNumber.length() != 0 ? secondNumber: "0");
        int longerStringCurrentIndex = longerStringRest.length() - 1;

        while(carrying != 0) {
            if(longerStringCurrentIndex >= 0) {
                int currentDigit = Character.getNumericValue(
                        longerStringRest.charAt(longerStringCurrentIndex));
                int currentSum = currentDigit + carrying;

                longerStringCurrentIndex -= 1;
                resultStringBuilder.insert(0, currentSum % this.decimalBase);
                carrying = currentSum / this.decimalBase;
            } else {
                resultStringBuilder.insert(0, carrying);
                carrying = 0;
            }
        }

        if(longerStringCurrentIndex != 0) {
            resultStringBuilder.insert(0,
                    longerStringRest.substring(0, longerStringCurrentIndex + 1));
        }

        return resultStringBuilder.toString();
    }

    public int getNumberOfDigits(int number) {
        int currentLength = 0;
        while(number > 0) {
            currentLength += 1;
            number /= this.decimalBase;
        }
        return currentLength;
    }

    public int getOldestDigit(int number) {
        int oldestDigit = 0;
        while(number > 0) {
            oldestDigit = number % this.decimalBase;
            number /= this.decimalBase;
        }
        return oldestDigit;
    }

    private int getNumberFromString(String number) {
        if (number.isEmpty()) {
            return 0;
        }
        return Integer.valueOf(number);
    }

    private String getCurrentChunk(String number) {
        return number.substring(Math.max(0, number.length() - this.chunkSize),
                number.length());
    }

    private String getChunkSliecedNumber(String number) {
        if (number.length() <= this.chunkSize) return "";
        int rightRange = number.length() - this.chunkSize >= 0 ?
                number.length() - this.chunkSize : number.length();
        return number.substring(0, rightRange);
    }

    private Map<Integer, Integer> getDigitsPositions(String number) {
        return IntStream.range(0, number.length()).boxed().
                collect(Collectors.toMap(i -> i,
                        i -> Character.getNumericValue(number.charAt(number.length() - 1 - i))));
    }
}


class BigNumberCalculatorValidator {

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
                String []values = line.split(";");

                String firstNumber = values[this.firstElementIndexInValidationFile];
                String secondNumber = values[this.secondElementIndexInValidationFile];
                String actualResult = values[this.sumElementIndexInValidationFile];
                String result = calculator.add2(firstNumber, secondNumber);

                if(result.equals(actualResult)) {
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