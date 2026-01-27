package com.lab5.bai2;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestSuiteRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(JunitTest.class);

        System.out.println("========== LAB5 - BAI 2: JUNIT TEST SUITE RESULTS ==========");
        System.out.println("Tests run: " + result.getRunCount());
        System.out.println("Failures: " + result.getFailureCount());
        System.out.println("Errors: " + result.getFailureCount());
        System.out.println("Success: " + result.wasSuccessful());
        System.out.println("Time elapsed: " + result.getRunTime() + " ms");
        
        if (!result.wasSuccessful()) {
            System.out.println("\n===== Failure Details =====");
            for (Failure failure : result.getFailures()) {
                System.out.println("  - " + failure.toString());
            }
        } else {
            System.out.println("\n✓ All tests passed successfully!");
        }
        
        System.out.println("============================================================");
    }
}
