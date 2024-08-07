package main.java.org.example;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BigNumberCalculator {

    private final int decimalBase = 10;
    private int chunkSize = 8;

    public BigNumberCalculator() { }
    public BigNumberCalculator(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    /**
     *
     * @param firstNumber
     * @param secondNumber
     * @return
     */
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

    /**
     *
     * @param firstNumber
     * @param secondNumber
     * @return
     */
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

    /**
     *
     * @param number
     * @return
     */
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

    /**
     *
     * @param number
     * @return
     */
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