package com.example;

import com.example.db.PerformanceTester;

public class Main {
    public static void main(String[] args) {
        PerformanceTester pt = new PerformanceTester();
        pt.generateAndSave(100_000);
    }
}