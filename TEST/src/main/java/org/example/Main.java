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


class BigNumberCalculator {

    private final int decimalBase = 10;
    private int chunkSize = 8;

    public BigNumberCalculator() { }
    public BigNumberCalculator(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public String addByPositions(String firstNumber, String secondNumber) {
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


    public String addWithChunks(String firstNumber, String secondNumber) {

        StringBuilder resultStringBuilder = new StringBuilder();
        long carrying = 0;

        while (!firstNumber.isEmpty() && !secondNumber.isEmpty()) {
            String firstStringCurrentChunk = getCurrentChunk(firstNumber);
            String secondStringCurrentChunk = getCurrentChunk(secondNumber);

            firstNumber = getChunkSliecedNumber(firstNumber);
            secondNumber = getChunkSliecedNumber(secondNumber);

            long currentChunkSum = 0;

            try {
                currentChunkSum = getNumberFromString(firstStringCurrentChunk) +
                        getNumberFromString(secondStringCurrentChunk) + carrying;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            resultStringBuilder.insert(0, currentChunkSum);

            if(getNumberOfDigits(currentChunkSum) > this.chunkSize) {
                carrying = getOldestDigit(currentChunkSum);
                resultStringBuilder.deleteCharAt(0);
            } else if(getNumberOfDigits(currentChunkSum) < this.chunkSize) {
                int digitsDifference = carrying == 0 ?
                        this.chunkSize - getNumberOfDigits(currentChunkSum) :
                        this.chunkSize - getNumberOfDigits(currentChunkSum) - 1;
                resultStringBuilder.insert(0, repeat("0",digitsDifference));
                carrying = 0;
            } else {
                carrying = 0;
            }

//            System.out.println(firstNumber);
//            System.out.println(firstStringCurrentChunk);
//            System.out.println(secondNumber);
//            System.out.println(secondStringCurrentChunk);
//            System.out.println(currentChunkSum);
//            System.out.println(resultStringBuilder);
        }


        String longerStringRest = firstNumber.length() != 0 ? firstNumber :
                (secondNumber.length() != 0 ? secondNumber: "0");
        int longerStringCurrentIndex = longerStringRest.length() - 1;

        while(carrying != 0) {
            if(longerStringCurrentIndex >= 0) {
                int currentDigit = Character.getNumericValue(
                        longerStringRest.charAt(longerStringCurrentIndex));
                long currentSum = currentDigit + carrying;

                longerStringCurrentIndex -= 1;
                resultStringBuilder.insert(0, currentSum % this.decimalBase);
                carrying = currentSum / this.decimalBase;
            } else {
                carrying = carrying == -1 ? 0 : carrying;
                resultStringBuilder.insert(0, carrying);
                carrying = 0;
            }
        }

        if(longerStringCurrentIndex != 0) {
            resultStringBuilder.insert(0,
                    longerStringRest.substring(0, longerStringCurrentIndex + 1));
        }

        while (resultStringBuilder.toString().startsWith("0")) {
            resultStringBuilder.deleteCharAt(0);
        }
        return resultStringBuilder.toString();
    }

    public int getNumberOfDigits(long number) {
        int currentLength = 0;
        if (number == 0) return 1;
        while(number > 0) {
            currentLength += 1;
            number /= this.decimalBase;
        }
        return currentLength;
    }

    public int getOldestDigit(long number) {
        int oldestDigit = 0;
        while(number > 0) {
            oldestDigit = (int) (number % this.decimalBase);
            number /= this.decimalBase;
        }
        return oldestDigit;
    }

    private long getNumberFromString(String number) {
        if (number.isEmpty()) {
            return 0;
        }
        return Long.parseLong(number);
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

    public String repeat(String stringToRepeat, int count) {
        return Collections.nCopies(count, stringToRepeat).
                stream().reduce("", (accumulator, str) -> accumulator + str);
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

                String firstNumber = values[this.firstElementIndexInValidationFile].trim();
                String secondNumber = values[this.secondElementIndexInValidationFile].trim();
                String actualResult = values[this.sumElementIndexInValidationFile].trim();
                String result = calculator.addWithChunks(firstNumber, secondNumber);

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