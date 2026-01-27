package com.example;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MathFuncTest.class);

        System.out.println("========== TEST RESULTS ==========");
        System.out.println("Tests run: " + result.getRunCount());
        System.out.println("Tests failed: " + result.getFailureCount());
        System.out.println("Tests ignored: " + result.getIgnoreCount());
        System.out.println("Success status: " + result.wasSuccessful());
        
        if (!result.wasSuccessful()) {
            System.out.println("\nFailure details:");
            for (Failure failure : result.getFailures()) {
                System.out.println("  - " + failure.toString());
            }
        }
        
        System.out.println("===================================");
    }
}
