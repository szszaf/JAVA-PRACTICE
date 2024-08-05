package org.example;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        BigNumberCalculator bigNumberCalculator = new BigNumberCalculator();
        System.out.println(bigNumberCalculator.add2(
                new String(new char[25]).replace("\0", "1"),
                new String(new char[30]).replace("\0", "9")
        ));
    }
}


class BigNumberCalculator {
    private final int decimalDivision = 10;

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
            int resultOnCurrentPosition = resultFromCurrentPositions % decimalDivision;
            result.put(i, resultOnCurrentPosition);
            carrying = resultFromCurrentPositions >= decimalDivision ? 1 : 0;
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
        int longerNumberLength = Math.max(firstNumber.length(), secondNumber.length());
        int shorterNumberLength = Math.min(firstNumber.length(), secondNumber.length());

        String longerStringRest = firstNumber.length() > secondNumber.length() ?
            firstNumber.substring(0, longerNumberLength - shorterNumberLength) :
            secondNumber.substring(0, longerNumberLength - shorterNumberLength);

        String longerStringPartial = firstNumber.length() > secondNumber.length() ?
                firstNumber.substring(longerNumberLength - shorterNumberLength) :
                secondNumber.substring(longerNumberLength - shorterNumberLength);




        return longerStringPartial;
    }

    private Map<Integer, Integer> getDigitsPositions(String number) {
        return IntStream.range(0, number.length()).boxed().
                collect(Collectors.toMap(i -> i,
                        i -> Character.getNumericValue(number.charAt(number.length() - 1 - i))));
    }
}