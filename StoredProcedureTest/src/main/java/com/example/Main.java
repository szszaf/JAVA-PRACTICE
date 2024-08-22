package com.example;

import com.example.db.PerformanceTester;

public class Main {
    public static void main(String[] args) {
        PerformanceTester performanceTester = new PerformanceTester();
        performanceTester.test(100_000, 1);
    }
}